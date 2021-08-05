# aop-part3-chapter06 - 중고거래앱

# 목차  

1. 인트로 (완성앱 & 구현 기능 소개)
2. 메인 페이지 Tab UI 구성하기
3. 상품 목록 페이지 UI 구성하기
4. Firebase  Realtime Database 를 활용하여 DB 구조 구상하기
5. Firebase에서 상품 목록 가져와 보여주기
6. Firebase Storage 를 이용하여 사진 업로드 추가하기
7. 마이페이지 구현하기
8. 채팅 리스트 구현하기
9. 채팅 페이지 구현하기
10. 어떤 것을 추가로 개발할 수 있을까?
11. 마무리
    

# 2021-0805
### firebase 관련 
* 파이어베이스 auth 를 이용한 회원가입 
- 이메일 회원가입 
- 프로파일 업데이트 

* 리얼타임 데이터베이스 
- 클래스 형태로 업로드 & 다운로드
 + 클래스에 빈 생성자 만들어야함. 
- addChildEventListener 

* 파이어베이스 파일 스토리지
 - 이미지파일 올리기 & 다운로드 
 + 다운로드 uri 를 저장해서, 불러올때 사용해야함. 

* Glide 라이브러리 활용 
 - 대부분 리싸이클러뷰에서, Glide 라이브러리 활용하여 이미지 불러온다. 
 + Glide.with(image.context)
 + Glide.load(model.uri)
 + Glide.into(image)

* 프래그먼트 
 - 프래그먼트 화면 이동 
 - 프래그먼트 라이프 싸이클
 + 디스트로이에, 리스트 클리어 시켜줘야함. 



# MARKDOWN
MARKDOWN 정리, 실습 for README.md

# 1. 제목(글머리) 작성
# H1 제목
## H2 부제목
### H3 소제목
#### H4 제목4
##### H5 제목5
###### H6 제목6


# 2. 번호 없는 리스트 작성
* 리스트1
    - 리스트2
        + 리스트3

# 3. 번호 있는 리스트 작성
1. 리스트1
2. 리스트2
3. 리스트3

# 4. 이텔릭체(기울어진 글씨) 작성
*텍스트*

# 5. 굵은 글씨 작성
**텍스트**

# 6. 인용
> 인용1

> 인용2
>> 인용안의 인용

# 7. 수평선 넣기

---

# 8. 링크 달기
(1) 인라인 링크

[블로그 주소](https://lsh424.tistory.com/)

(2) 참조 링크

[블로그 주소][blog]

[blog]: https://lsh424.tistory.com/

# 9. 이미지 추가하기
![이탈리아 포지타노](https://user-images.githubusercontent.com/31477658/85016059-f962aa80-b1a3-11ea-8c91-dacba2666b78.jpeg)

### 이미지 사이즈 조절
<img src="https://user-images.githubusercontent.com/31477658/85016059-f962aa80-b1a3-11ea-8c91-dacba2666b78.jpeg"  width="700" height="370">

### 이미지 파일로 추가하기
<img src="Capri_Island.jpeg" width="700">

# 10. 코드블럭 추가하기

```swift
public struct CGSize {
  public var width: CGFloat
  public var heigth: CGFloat
  ...
}
```

# etc

**텍스트 굵게**  
~~텍스트 취소선~~

### [개행]

스페이스바를 통한 문장개행  
스페이스바를 통한 문장개행

br태그를 사용한 문장개행
<br>
<br>
br태그를 사용한 문장개행


### [체크박스]

다음과 같이 체크박스를 표현 할 수 있습니다.
* [x] 체크박스
* [ ] 빈 체크박스
* [ ] 빈 체크박스

### [이모지 넣기]
❤️💜💙🤍

### [표 넣기]
|왼쪽 정렬|가운데 정렬|오른쪽 정렬| 
|:---|:---:|---:| 
|내용1|내용2|내용3| 
|내용1|내용2|내용3| 

<br>

### 정리내용
[정리 내용 보기](https://lsh424.tistory.com/37)    
