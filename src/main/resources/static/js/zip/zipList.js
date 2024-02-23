document.querySelector("a.nav-link").addEventListener('click', (e) => {
    const { zipId } = e.target.dataset;
    if (zipId) {
        location.href = `${contextPath}zip/zipDetail.do?id=${zipId}`;
    }
});

document.getElementById("searchForm").addEventListener("submit", function(event) {
    event.preventDefault(); // 폼 기본 동작 방지

    // 폼 데이터 가져오기
    var formData = new FormData(this);

    // POST 요청 보내기
    fetch("/onezip/zip/zipSearch", {
        method: "POST",
        headers : {
            'X-CSRF-TOKEN' : document.getElementById("csrf").value
        },
        body: formData
    })
        .then(response => response.json())
        .then(data => {
            // 검색 결과 처리
            if (data) {
                // 검색된 집 정보가 있을 경우 집 상세 페이지로 이동
                location.href = '/onezip/zip/zipDetail.do?id='+data.zipId;
            } else {
                // 검색된 집 정보가 없을 경우 오류 메시지 표시
                displayErrorMessage('error');
            }
        })
        .catch(error => {
            // 오류 처리
            console.error("Error:", error);
        });
});

function displayErrorMessage(errorMessage) {
    // 오류 메시지를 화면에 표시하는 로직 작성
    // 예: 오류 메시지를 화면에 표시하는 div 요소를 만들어서 내용 채우기
}