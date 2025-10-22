-- 스터디 카페 데이터베이스 스키마 생성
-- Study Cafe Database Schema Creation

-- 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS studycafe 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- 데이터베이스 사용
USE studycafe;

-- 회원 테이블 생성
CREATE TABLE IF NOT EXISTS member (
    ID VARCHAR(50) PRIMARY KEY COMMENT '회원 ID',
    password VARCHAR(255) NOT NULL COMMENT '비밀번호',
    name VARCHAR(100) NOT NULL COMMENT '이름',
    phone_number VARCHAR(20) COMMENT '전화번호',
    time INT DEFAULT 0 COMMENT '남은 시간(분)',
    seat_number INT DEFAULT NULL COMMENT '현재 사용 중인 좌석 번호',
    is_using BOOLEAN DEFAULT FALSE COMMENT '현재 이용 중인지 여부',
    buy_time TIMESTAMP NULL DEFAULT NULL COMMENT '이용 시작 시간',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '가입일',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='회원 정보';

-- 좌석 테이블 생성
CREATE TABLE IF NOT EXISTS seat (
    seat_number INT PRIMARY KEY COMMENT '좌석 번호',
    is_occupied BOOLEAN DEFAULT FALSE COMMENT '사용 중인지 여부',
    user_id VARCHAR(50) DEFAULT NULL COMMENT '사용 중인 사용자 ID',
    remaining_time INT DEFAULT 0 COMMENT '남은 시간(분)',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
    FOREIGN KEY (user_id) REFERENCES member(ID) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='좌석 정보';

-- 관리자 테이블 생성
CREATE TABLE IF NOT EXISTS admin (
    admin_id VARCHAR(50) PRIMARY KEY COMMENT '관리자 ID',
    password VARCHAR(255) NOT NULL COMMENT '비밀번호',
    name VARCHAR(100) NOT NULL COMMENT '관리자 이름',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='관리자 정보';

-- 결제 내역 테이블 생성
CREATE TABLE IF NOT EXISTS payment (
    payment_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '결제 ID',
    user_id VARCHAR(50) NOT NULL COMMENT '사용자 ID',
    seat_number INT NOT NULL COMMENT '좌석 번호',
    payment_type ENUM('member', 'nonmember') NOT NULL COMMENT '결제 유형',
    amount INT NOT NULL COMMENT '결제 금액',
    time_purchased INT NOT NULL COMMENT '구매한 시간(분)',
    payment_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '결제 시간',
    FOREIGN KEY (user_id) REFERENCES member(ID) ON DELETE CASCADE,
    FOREIGN KEY (seat_number) REFERENCES seat(seat_number) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='결제 내역';

-- 좌석 데이터 초기화 (60개 좌석)
INSERT INTO seat (seat_number) VALUES 
(1), (2), (3), (4), (5), (6), (7), (8), (9), (10),
(11), (12), (13), (14), (15), (16), (17), (18), (19), (20),
(21), (22), (23), (24), (25), (26), (27), (28), (29), (30),
(31), (32), (33), (34), (35), (36), (37), (38), (39), (40),
(41), (42), (43), (44), (45), (46), (47), (48), (49), (50),
(51), (52), (53), (54), (55), (56), (57), (58), (59), (60)
ON DUPLICATE KEY UPDATE seat_number = VALUES(seat_number);

-- 기본 관리자 계정 생성
INSERT INTO admin (admin_id, password, name) VALUES 
('admin', 'admin123', '관리자')
ON DUPLICATE KEY UPDATE admin_id = VALUES(admin_id);

-- 테스트용 회원 계정 생성
INSERT INTO member (ID, password, name, phone_number, time) VALUES 
('test01', 'password123', '테스트회원1', '010-1234-5678', 120),
('test02', 'password123', '테스트회원2', '010-2345-6789', 60)
ON DUPLICATE KEY UPDATE ID = VALUES(ID);

-- 인덱스 생성
CREATE INDEX idx_member_using ON member(is_using);
CREATE INDEX idx_member_seat ON member(seat_number);
CREATE INDEX idx_seat_occupied ON seat(is_occupied);
CREATE INDEX idx_seat_user ON seat(user_id);
CREATE INDEX idx_payment_user ON payment(user_id);
CREATE INDEX idx_payment_time ON payment(payment_time);

-- 뷰 생성 (현재 이용 중인 회원 정보)
CREATE OR REPLACE VIEW active_members AS
SELECT 
    m.ID,
    m.name,
    m.phone_number,
    m.time as remaining_time,
    m.seat_number,
    m.buy_time,
    TIMESTAMPDIFF(MINUTE, m.buy_time, NOW()) as used_time
FROM member m
WHERE m.is_using = TRUE;

-- 뷰 생성 (좌석 현황)
CREATE OR REPLACE VIEW seat_status AS
SELECT 
    s.seat_number,
    s.is_occupied,
    CASE 
        WHEN s.is_occupied THEN m.name
        ELSE NULL
    END as user_name,
    CASE 
        WHEN s.is_occupied THEN m.time
        ELSE NULL
    END as remaining_time
FROM seat s
LEFT JOIN member m ON s.user_id = m.ID AND m.is_using = TRUE;

-- 프로시저 생성 (회원 체크인)
DELIMITER //
CREATE PROCEDURE CheckInMember(
    IN p_user_id VARCHAR(50),
    IN p_seat_number INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    START TRANSACTION;
    
    -- 좌석이 비어있는지 확인
    IF (SELECT is_occupied FROM seat WHERE seat_number = p_seat_number) = TRUE THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Seat is already occupied';
    END IF;
    
    -- 회원이 이미 이용 중인지 확인
    IF (SELECT is_using FROM member WHERE ID = p_user_id) = TRUE THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Member is already using a seat';
    END IF;
    
    -- 좌석 점유
    UPDATE seat SET is_occupied = TRUE, user_id = p_user_id WHERE seat_number = p_seat_number;
    
    -- 회원 상태 업데이트
    UPDATE member SET 
        seat_number = p_seat_number, 
        is_using = TRUE, 
        buy_time = NOW() 
    WHERE ID = p_user_id;
    
    COMMIT;
END //
DELIMITER ;

-- 프로시저 생성 (회원 체크아웃)
DELIMITER //
CREATE PROCEDURE CheckOutMember(
    IN p_user_id VARCHAR(50)
)
BEGIN
    DECLARE v_seat_number INT;
    
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    START TRANSACTION;
    
    -- 회원의 좌석 번호 조회
    SELECT seat_number INTO v_seat_number FROM member WHERE ID = p_user_id AND is_using = TRUE;
    
    IF v_seat_number IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Member is not using any seat';
    END IF;
    
    -- 좌석 해제
    UPDATE seat SET is_occupied = FALSE, user_id = NULL WHERE seat_number = v_seat_number;
    
    -- 회원 상태 업데이트
    UPDATE member SET 
        seat_number = NULL, 
        is_using = FALSE, 
        buy_time = NULL 
    WHERE ID = p_user_id;
    
    COMMIT;
END //
DELIMITER ;

-- 프로시저 생성 (시간 만료된 회원 자동 체크아웃)
DELIMITER //
CREATE PROCEDURE AutoCheckOutExpiredMembers()
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    START TRANSACTION;
    
    -- 시간이 만료된 회원들 체크아웃
    UPDATE seat s
    JOIN member m ON s.user_id = m.ID
    SET s.is_occupied = FALSE, s.user_id = NULL
    WHERE m.is_using = TRUE AND m.time <= 0;
    
    UPDATE member 
    SET seat_number = NULL, is_using = FALSE, buy_time = NULL
    WHERE is_using = TRUE AND time <= 0;
    
    COMMIT;
END //
DELIMITER ;

-- 이벤트 스케줄러 생성 (매분마다 만료된 회원 체크아웃)
-- SET GLOBAL event_scheduler = ON;
-- CREATE EVENT IF NOT EXISTS auto_checkout_expired
-- ON SCHEDULE EVERY 1 MINUTE
-- DO CALL AutoCheckOutExpiredMembers();

-- 권한 설정 (필요한 경우)
-- GRANT SELECT, INSERT, UPDATE, DELETE ON studycafe.* TO 'studycafe_user'@'%';
-- FLUSH PRIVILEGES;

-- 완료 메시지
SELECT 'Database setup completed successfully!' as message;
