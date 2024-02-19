$('#addNeighborForm').submit(function(event) {
    event.preventDefault(); // 기본 제출 동작 방지
    var formData = $(this).serialize(); // 폼 데이터 가져오기
    $.ajax({
        type: 'POST',
        url: '/onezip/neighbors/add',
        headers : {
            'X-CSRF-TOKEN' : $('#addNeighborForm').find("[name=nCsrf]").val()
        },
        data: formData,
        success: function(response) {
            // 이웃 신청이 성공한 경우 처리할 로직 추가
            alert(response);
        },
        error: function(xhr, status, error) {
            // 이웃 신청이 실패한 경우 처리할 로직 추가
            var errorMessage = xhr.responseText;
            alert(errorMessage);
        }
    });
});
