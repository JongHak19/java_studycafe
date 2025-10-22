```md
# 스터디 카페 Kiosk 리팩토링 전략 (agent.md)

이 문서는 `MainFrame.java`라는 단일 파일("God File")에 모든 로직이 포함된 스터디 카페 키오스크 애플리케이션을 리팩토링하기 위한 전략을 정의합니다.

**핵심 원칙: 관심사의 분리 (Separation of Concerns, SoC)**
모든 코드를 **UI(View)**, **비즈니스 로직(Service)**, **데이터베이스(Repository)**라는 세 가지 주요 계층으로 분리합니다.

---

## 1단계: 패키지 구조화

가장 먼저, 코드를 분리하여 담을 표준 디렉토리 구조를 생성합니다.

```

src/
└── studycafe/
├── main/
│   └── Main.java             \# main() 메소드를 포함한 애플리케이션 시작점
├── ui/                     \# 모든 UI (JFrame, JPanel) 클래스
│   ├── MainFrame.java
│   ├── SeatPanel.java
│   ├── LoginPanel.java
│   └── admin/
│       ├── AdminMainPanel.java
│       └── MemberManagePanel.java
├── model/ (or domain)      \# 데이터 전송 객체 (DTO/VO)
│   ├── Member.java
│   └── Seat.java
├── db/ (or repository)     \# 모든 데이터베이스(JDBC) 로직
│   └── StudyCafeRepository.java
├── service/ (or controller) \# 모든 비즈니스 로직
│   ├── AuthService.java
│   ├── SeatService.java
│   └── TimerService.java
└── AppContext.java         \# 의존성 주입(DI) 및 전역 상태 관리를 위한 컨테이너

````

---

## 2단계: UI(View) 분리

`MainFrame` 내부에 정의된 모든 **내부 클래스(Inner Class)**들을 `studycafe.ui` 패키지 아래의 별도 `.java` 파일로 분리합니다.

* `class seat` -> `SeatPanel.java`
* `class main_panel` -> `ChoicePanel.java`
* `class new_seat` -> `SeatMovePanel.java` (나중에 `SeatPanel`과 통합)
* `class member_view` -> `MemberLoginPanel.java`
* `class join_view` -> `MemberJoinPanel.java`
* `class AdminPanel` -> `AdminMainPanel.java`
* `class User` -> `MemberManagePanel.java`
* `class sales` -> `SalesGraphPanel.java`

**규칙:** UI 클래스는 화면을 그리거나 사용자 입력을 받는 역할만 수행합니다. **절대로** `java.sql` 관련 코드를 포함해서는 안 됩니다.

---

## 3단계: 데이터 접근 계층 (Repository) 생성

모든 JDBC 및 SQL 쿼리 로직을 `studycafe.db.StudyCafeRepository.java` 클래스로 이동시킵니다.

**변경 전 (UI 클래스 내부):**
```java
// studycafe/ui/MemberLoginPanel.java (Bad)
login_m.addActionListener(e -> {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url, ...);
        // ... SQL 실행 ...
    } catch (SQLException ex) {
        // ...
    }
});
````

**변경 후 (Repository 분리):**
`studycafe/db/StudyCafeRepository.java`

```java
package studycafe.db;

// ... imports ...

public class StudyCafeRepository {
    private String url = "jdbc:mysql://...";
    // ... (DB 접속 정보)

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, mysql_user, mysql_password);
    }

    // try-with-resources 구문으로 리소스 자동 해제
    public Member findMemberByIdAndPassword(String id, String password) {
        String sql = "SELECT * FROM member WHERE ID = ? AND password = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, id);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Member 모델 객체로 변환하여 반환
                    return new Member(/* ... rs에서 데이터 추출 ... */);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 로깅 처리
        }
        return null;
    }

    // ... (모든 DB 관련 메소드: updateSeat, createMember, findAllMembers 등)
}
```

-----

## 4단계: 비즈니스 로직 (Service) 분리

UI와 Repository 사이에서 실제 "기능"을 처리하는 `Service` 클래스를 생성합니다.

`studycafe/service/AuthService.java`

```java
package studycafe.service;

import studycafe.db.StudyCafeRepository;
import studycafe.model.Member;

public class AuthService {
    private StudyCafeRepository repository;

    // 생성자를 통해 Repository를 주입받음
    public AuthService(StudyCafeRepository repository) {
        this.repository = repository;
    }

    public Member login(String id, String password) {
        // 유효성 검사
        if (id == null || id.isEmpty() || password == null || password.isEmpty()) {
            return null;
        }
        // DB 작업은 Repository에 위임
        return repository.findMemberByIdAndPassword(id, password);
    }
    
    public boolean register(Member newMember) {
        // ... 회원가입 관련 로직 ...
        // repository.createMember(newMember);
        return true;
    }
}
```

-----

## 5단계: 전역 상태 관리 (Static 변수 제거)

`static int selected_seat`와 같은 정적 변수 사용을 중단합니다. `AppContext` 클래스를 통해 필요한 객체와 상태를 관리하고, 생성자를 통해 각 클래스에 \*\*주입(Dependency Injection)\*\*합니다.

`studycafe/AppContext.java`

```java
package studycafe;

import studycafe.db.StudyCafeRepository;
import studycafe.service.*;

// 앱 전역에서 사용되는 객체들을 생성하고 보관하는 컨테이너
public class AppContext {
    // 계층별 객체
    private final StudyCafeRepository repository;
    private final AuthService authService;
    private final SeatService seatService;
    private final TimerService timerService;
    
    // 전역 상태
    private int currentSelectedSeat = -1;
    private String currentUserId = null;

    public AppContext() {
        // 의존성 순서에 맞게 객체 생성
        this.repository = new StudyCafeRepository();
        this.authService = new AuthService(repository);
        this.seatService = new SeatService(repository);
        this.timerService = new TimerService(repository);
    }

    // Getter
    public AuthService getAuthService() { return authService; }
    public SeatService getSeatService() { return seatService; }
    public TimerService getTimerService() { return timerService; }

    // 전역 상태 Getter/Setter
    public void setSelectedSeat(int seat) { this.currentSelectedSeat = seat; }
    public int getSelectedSeat() { return this.currentSelectedSeat; }
    // ...
}
```

-----

## 6단계: 화면 전환 로직 (CardLayout)

`removeAll()`과 `add()`를 반복하는 대신, `CardLayout`을 사용하여 패널을 미리 등록하고 효율적으로 전환합니다.

`studycafe/ui/MainFrame.java`

```java
package studycafe.ui;

import javax.swing.*;
import java.awt.*;
import studycafe.AppContext;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel; // 모든 패널을 담을 부모 패널
    private AppContext context;

    public MainFrame() {
        this.context = new AppContext(); // AppContext 생성
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // 패널 생성 시 AppContext와 화면 전환용 콜백(메소드 참조)을 주입
        SeatPanel seatPanel = new SeatPanel(context, this::showPanel);
        MemberLoginPanel loginPanel = new MemberLoginPanel(context, this::showPanel);
        
        // CardLayout에 패널 추가
        mainPanel.add(seatPanel, "SEAT_PANEL");
        mainPanel.add(loginPanel, "LOGIN_PANEL");

        add(mainPanel);
        // ... (프레임 설정) ...
        
        // 타이머 서비스 시작
        context.getTimerService().startService();
    }

    // 패널들이 화면 전환을 요청할 때 호출할 공용 메소드
    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }
}
```

-----

## 7단계: 타이머 로직 (ScheduledExecutorService)

`Thread[]` 배열과 위험한 `Thread.stop()`을 제거합니다. `ScheduledExecutorService`를 사용하여 단일 스레드로 안전하게 시간 차감 로직을 관리합니다.

`studycafe/service/TimerService.java`

```java
package studycafe.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import studycafe.db.StudyCafeRepository;

public class TimerService {
    private StudyCafeRepository repository;
    private ScheduledExecutorService scheduler;

    public TimerService(StudyCafeRepository repository) {
        this.repository = repository;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    public void startService() {
        // 1초(혹은 1분)마다 updateUsageTime 메소드 실행
        scheduler.scheduleAtFixedRate(this::updateUsageTime, 0, 1, TimeUnit.SECONDS); 
    }

    private void updateUsageTime() {
        // DB의 모든 활성 사용자 시간을 차감하는 *단일 SQL 쿼리* 호출
        // repository.decrementAllActiveMemberTimes();
        
        // 시간이 만료된 사용자를 찾아 일괄 퇴실 처리
        // repository.checkoutExpiredUsers();
    }
    
    public void stopService() {
        scheduler.shutdown(); // 안전하게 스레드 종료
    }
}
```

-----

## 8단계: 중복 코드 제거 (DRY - Don't Repeat Yourself)

`seat`와 `new_seat`처럼 중복되는 UI 코드를 하나로 통합합니다. \*\*전략 패턴(Strategy Pattern)\*\*을 사용하여 클릭 시 동작만 다르게 주입합니다.

`studycafe/ui/SeatPanel.java`

```java
package studycafe.ui;

import javax.swing.*;
import java.awt.event.ActionListener;

public class SeatPanel extends JPanel {
    private JButton[] seats = new JButton[60];
    
    // 생성자에서 "좌석 클릭" 이벤트를 외부에서 주입받음
    public SeatPanel(ActionListener onSeatClick) {
        // ... (좌석 UI 배치 코드) ...
        
        for (int i = 0; i < 60; i++) {
            seats[i].setActionCommand(String.valueOf(i + 1)); // 좌석 번호 설정
            seats[i].addActionListener(onSeatClick); // 주입받은 리스너 연결
        }
    }
    
    // DB 데이터로 좌석 색상을 새로고침하는 메소드
    public void refreshSeatStatus(List<Seat> seatData) {
        // ...
    }
}
```

이를 통해 `MainFrame`에서 `SeatPanel`을 생성할 때, 좌석 선택용 리스너와 좌석 이동용 리스너를 각각 다르게 전달하여 재사용할 수 있습니다.

-----

---

## 9단계: 예외 처리 및 로깅 개선

**예외 처리 전략:**
- `SQLException`을 `RuntimeException`으로 래핑하여 Service 계층에서 처리
- 사용자 친화적인 에러 메시지 제공
- 로깅 프레임워크 도입 (SLF4J + Logback)

```java
// studycafe/exception/StudyCafeException.java
public class StudyCafeException extends RuntimeException {
    public StudyCafeException(String message, Throwable cause) {
        super(message, cause);
    }
}

// studycafe/db/StudyCafeRepository.java
public Member findMemberByIdAndPassword(String id, String password) {
    String sql = "SELECT * FROM member WHERE ID = ? AND password = ?";
    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        // ... 쿼리 실행 ...
    } catch (SQLException e) {
        logger.error("로그인 실패: ID={}", id, e);
        throw new StudyCafeException("로그인 처리 중 오류가 발생했습니다.", e);
    }
}
```

---

## 10단계: 설정 관리 (Configuration)

**하드코딩된 설정값들을 외부 설정 파일로 분리:**

```java
// studycafe/config/AppConfig.java
public class AppConfig {
    private static final Properties props = new Properties();
    
    static {
        try (InputStream is = AppConfig.class.getResourceAsStream("/application.properties")) {
            props.load(is);
        } catch (IOException e) {
            throw new StudyCafeException("설정 파일 로드 실패", e);
        }
    }
    
    public static String getDatabaseUrl() {
        return props.getProperty("db.url");
    }
    
    public static int getTimerInterval() {
        return Integer.parseInt(props.getProperty("timer.interval", "60"));
    }
}
```

---

## 11단계: 이벤트 기반 아키텍처 (Observer Pattern)

**UI와 비즈니스 로직 간의 느슨한 결합을 위한 이벤트 시스템:**

```java
// studycafe/event/SeatSelectedEvent.java
public class SeatSelectedEvent {
    private final int seatNumber;
    private final String userId;
    
    // 생성자, getter...
}

// studycafe/event/EventBus.java
public class EventBus {
    private final Map<Class<?>, List<Consumer<?>>> listeners = new HashMap<>();
    
    public <T> void subscribe(Class<T> eventType, Consumer<T> listener) {
        listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
    }
    
    public void publish(Object event) {
        listeners.getOrDefault(event.getClass(), Collections.emptyList())
                .forEach(listener -> ((Consumer<Object>) listener).accept(event));
    }
}
```

---

## 12단계: 데이터 검증 및 비즈니스 규칙

**입력 데이터 검증을 위한 Validator 클래스:**

```java
// studycafe/validation/MemberValidator.java
public class MemberValidator {
    public static ValidationResult validateMember(Member member) {
        ValidationResult result = new ValidationResult();
        
        if (member.getId() == null || member.getId().length() < 3) {
            result.addError("ID는 3자 이상이어야 합니다.");
        }
        
        if (member.getPassword() == null || member.getPassword().length() < 6) {
            result.addError("비밀번호는 6자 이상이어야 합니다.");
        }
        
        return result;
    }
}
```

---

## 13단계: 성능 최적화

**데이터베이스 연결 풀링 및 캐싱:**

```java
// studycafe/db/ConnectionPool.java
public class ConnectionPool {
    private final HikariDataSource dataSource;
    
    public ConnectionPool() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(AppConfig.getDatabaseUrl());
        config.setMaximumPoolSize(10);
        this.dataSource = new HikariDataSource(config);
    }
    
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
```

---

## 14단계: 테스트 코드 작성

**각 계층별 단위 테스트 및 통합 테스트:**

```java
// test/java/studycafe/service/AuthServiceTest.java
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private StudyCafeRepository repository;
    
    @InjectMocks
    private AuthService authService;
    
    @Test
    void 로그인_성공_테스트() {
        // Given
        when(repository.findMemberByIdAndPassword("test", "password"))
            .thenReturn(new Member("test", "password"));
        
        // When
        Member result = authService.login("test", "password");
        
        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("test");
    }
}
```

---

## 리팩토링의 이점

1. **유지보수성:** 버그 발생 시 `ui`, `service`, `db` 중 문제 영역 특정이 용이합니다.
2. **테스트 용이성:** 각 계층을 독립적으로 테스트할 수 있습니다.
3. **확장성:** 신규 기능 추가 시 관련된 패키지에 파일만 추가하면 됩니다.
4. **안정성:** 위험한 `Thread.stop()`을 제거하고 안전한 `ExecutorService`로 대체합니다.
5. **성능:** 연결 풀링과 캐싱으로 데이터베이스 성능 향상
6. **확장성:** 이벤트 기반 아키텍처로 새로운 기능 추가가 용이
7. **신뢰성:** 포괄적인 예외 처리와 로깅으로 안정성 향상

---

## 리팩토링 실행 순서 권장사항

1. **1-2단계:** 패키지 구조화 및 UI 분리 (기반 작업)
2. **3-4단계:** Repository와 Service 계층 분리 (핵심 아키텍처)
3. **5-6단계:** 상태 관리 및 화면 전환 개선 (사용자 경험)
4. **7-8단계:** 타이머 로직 개선 및 중복 코드 제거 (안정성)
5. **9-12단계:** 예외 처리, 설정 관리, 이벤트 시스템, 검증 로직 (품질)
6. **13-14단계:** 성능 최적화 및 테스트 코드 (완성도)

각 단계마다 기존 기능이 정상 작동하는지 확인하며 점진적으로 진행하는 것을 권장합니다.