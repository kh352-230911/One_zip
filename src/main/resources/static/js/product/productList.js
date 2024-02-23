document.querySelectorAll("tr[data-product-id]").forEach((tr) => {
    tr.addEventListener('click', (e) => {
        const td = e.target;
        const tr = td.parentElement;
        const {productId} = tr.dataset;
        location.href = `${contextPath}product/productDetail.do?id=${productId}`;
    });
});
//
//
// document.querySelector("under10000btn").addEventListener("click", (e) => {
//     // ajax주요 속성은 notion에서 확인 가능
//     $.ajax({
//         // url
//         url : `${contextPath}product/text`,
//         // 서버 요청 방식(get/post/put - default:get)
//         method : "get",
//         // 보내는 객체, javascript객체로 전달하면 자동으로 직렬화 수행.
//         // 요청 parameter 설정
//         data : {
//             refPrice: "10000",
//             isUnder : "true"
//         },
//         // 서버에서 response 오는 데이터의 데이터 형 설정(xml, json, script, html, text)
//         // 값이 없다면 스마트하게 판단(응답헤더의 content-type을 보고 판단)
//         // 응답 데이터의 content-type을 override함 응답에는 content-type이 html로 돼있어도 text로 적는다면, text형식으로 보내짐
//         dataType : "text",
//         // ajax 요청을 하기 전 실행 되는 이벤트 callback 함수
//         // 전송하기 전 호출
//         beforeSend() {
//             console.log('ajax호출 전');
//         },
//         // 객체안에 메소드를 작성하는 문법
//         // 전송이 성공할 경우 처리할 핸들러
//         success(response){
//             console.log('ajax성공 후');
//         },
//         // 전송이 실패할 경우 처리할 핸들러
//         // error에는 jqXHR, textStatus, errorThrown의 인자가 넘어옴
//         error(jqXHR, textStatus, errorThrown) {
//             console.log('ajax실패');
//             console.log('error');
//         },
//         // 전송의 실패 유무와 상관없이 무조건 실행
//         complete() {
//             console.log('ajax성공');
//         }
//
//     });
// });
//
// document.querySelector("under50000btn").addEventListener("click", (e) => {
//     // ajax주요 속성은 notion에서 확인 가능
//     $.ajax({
//         // url
//         url : `${contextPath}product/text`,
//         // 서버 요청 방식(get/post/put - default:get)
//         method : "get",
//         // 보내는 객체, javascript객체로 전달하면 자동으로 직렬화 수행.
//         // 요청 parameter 설정
//         data : {
//             refPrice: "50000",
//             isUnder : "true"
//         },
//         // 서버에서 response 오는 데이터의 데이터 형 설정(xml, json, script, html, text)
//         // 값이 없다면 스마트하게 판단(응답헤더의 content-type을 보고 판단)
//         // 응답 데이터의 content-type을 override함 응답에는 content-type이 html로 돼있어도 text로 적는다면, text형식으로 보내짐
//         dataType : "text",
//         // ajax 요청을 하기 전 실행 되는 이벤트 callback 함수
//         // 전송하기 전 호출
//         beforeSend() {
//             console.log('ajax호출 전');
//         },
//         // 객체안에 메소드를 작성하는 문법
//         // 전송이 성공할 경우 처리할 핸들러
//         success(response){
//             console.log('ajax성공 후');
//         },
//         // 전송이 실패할 경우 처리할 핸들러
//         // error에는 jqXHR, textStatus, errorThrown의 인자가 넘어옴
//         error(jqXHR, textStatus, errorThrown) {
//             console.log('ajax실패');
//             console.log('error');
//         },
//         // 전송의 실패 유무와 상관없이 무조건 실행
//         complete() {
//             console.log('ajax성공');
//         }
//
//     });
// });
//
// document.querySelector("under100000btn").addEventListener("click", (e) => {
//     // ajax주요 속성은 notion에서 확인 가능
//     $.ajax({
//         // url
//         url : `${contextPath}product/text`,
//         // 서버 요청 방식(get/post/put - default:get)
//         method : "get",
//         // 보내는 객체, javascript객체로 전달하면 자동으로 직렬화 수행.
//         // 요청 parameter 설정
//         data : {
//             refPrice: "100000",
//             isUnder : "false"
//         },
//         // 서버에서 response 오는 데이터의 데이터 형 설정(xml, json, script, html, text)
//         // 값이 없다면 스마트하게 판단(응답헤더의 content-type을 보고 판단)
//         // 응답 데이터의 content-type을 override함 응답에는 content-type이 html로 돼있어도 text로 적는다면, text형식으로 보내짐
//         dataType : "text",
//         // ajax 요청을 하기 전 실행 되는 이벤트 callback 함수
//         // 전송하기 전 호출
//         beforeSend() {
//             console.log('ajax호출 전');
//         },
//         // 객체안에 메소드를 작성하는 문법
//         // 전송이 성공할 경우 처리할 핸들러
//         success(response){
//             console.log('ajax성공 후');
//         },
//         // 전송이 실패할 경우 처리할 핸들러
//         // error에는 jqXHR, textStatus, errorThrown의 인자가 넘어옴
//         error(jqXHR, textStatus, errorThrown) {
//             console.log('ajax실패');
//             console.log('error');
//         },
//         // 전송의 실패 유무와 상관없이 무조건 실행
//         complete() {
//             console.log('ajax성공');
//         }
//
//     });
// });