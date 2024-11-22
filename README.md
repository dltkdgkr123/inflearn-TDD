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

![image1.png](/images/image1.png)