# TDD

# 잡기술 (Windows/IntelliJ 환경)

### ⌨️ **코드 역순 구현**

- **`productRepository.save(product);` 구현 시**

**기존 순서**

1. `ProductRepository` 구현
2. `ProductRepository.save()` 구현
3. `Product` 구현
4. `productRepository.save(product);` 작성

**영상 순서**

1. `productRepository.save(product);` 작성 (***동작하지 않는 코드***)
2. `Alt + Enter` **(Show Context Menu/Quick Fix)**를 사용해 필요한 객체를 자동으로 구현

---

### ⌨️ **테스트 코드 작성**

**기존 순서**

1. 관련 패키지/모듈 구현
2. 테스트 코드에서 해당 코드를 import하여 작성
3. 동작 성공 확인

**영상 순서**

1. 테스트 코드에 모든 기능을 한 번에 구현
2. 동작 성공 확인
3. `Refactor` 기능을 사용해 모든 inner class를 extern class로 변경
    - `Ctrl + Alt + M`: 특정 코드 블록을 메서드로 추출하는 리팩토링
    - `F6`: inner class를 외부 클래스로 추출하는 리팩토링
    

---

### ⌨️ **템플릿 기능 활용**

- 파라미터 `final` 자동화
- 기본 `throw Exception` 처리

---

### ⌨️ **가독성을 위한 선택지**

- Test 단위에 한해서 `snake case` 사용 : inner class는 미적용
- 변수 키워드 `var` 사용 : java 10부터 도입된 타입 추론(Type Inference) 기반

---

### ⌨️ **[Java] record 키워드**
- `새로운 class type` : java 14부터 도입, java 16부터 정식 기능 포함
- `불변 객체`를 쉽게 만들 수 있음
- 기존 DTO의 `보일러 플레이트 코드` 문제 해결 : getter, setter, equals, hashCode, toString 등
- Lombok이 이를 대신 해결했으나, java 진영에서도 근본적인 해결을 위함
- see also : https://blog.naver.com/seek316/223341255150

![image1.png](/images/image1.png)