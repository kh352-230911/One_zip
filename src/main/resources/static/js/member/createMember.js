$(document).ready(function() {
    // 비밀번호 확인 일치 검사
    $("#passwordConfirmation").blur(function() {
        const password = $("#password").val();
        const passwordConfirmation = $(this).val();
        if (password !== passwordConfirmation) {
            alert("패스워드가 일치하지 않습니다.");
            $("#password").select();
        }
    });

    // 폼 제출 시 유효성 검사
    $("form[name='memberCreateFrm']").submit(function(e) {
        const memberId = $("#memberId").val().trim();
        const idDuplicateCheck = $("#idDuplicateCheck").val();
        const password = $("#password").val();
        const passwordConfirmation = $("#passwordConfirmation").val();
        const name = $("#name").val();

        if (!/^\w{4,}$/.test(memberId)) {
            alert("아이디는 영문자, 숫자, _ 4자리이상 입력하세요.");
            $("#memberId").select();
            e.preventDefault(); // 폼 제출 방지
            return false;
        }
        if (idDuplicateCheck == "0") {
            alert("사용 가능한 아이디를 입력해주세요.");
            $("#memberId").select();
            e.preventDefault(); // 폼 제출 방지
            return false;
        }
        if (password !== passwordConfirmation) {
            alert("패스워드가 일치하지 않습니다.");
            $("#password").select();
            e.preventDefault(); // 폼 제출 방지
            return false;
        }
        if (!/^[\w가-힣]{2,}$/.test(name)) {
            alert("이름을 2글자 이상 입력하세요.");
            $("#name").select();
            e.preventDefault(); // 폼 제출 방지
            return false;
        }
    });

    // 아이디 중복 검사
    $("#memberId").keyup(function() {
        const memberId = $(this).val().trim();
        const guideOk = $(".guide.ok");
        const guideError = $(".guide.error");

        if (!/^\w{4,}$/.test(memberId)) {
            guideError[0].style.display = "none"; // jQuery 객체에서 첫 번째 DOM 엘리먼트를 참조
            guideOk[0].style.display = "none";    // jQuery 객체에서 첫 번째 DOM 엘리먼트를 참조
            $("#idDuplicateCheck").val("0");
            return;
        }

        $.ajax({
            url: `${contextPath}/member/checkIdDuplicate.do`,
            method: 'post',
            headers: {
                [csrfHeaderName]: csrfToken
            },
            data: {
                memberId: memberId
            },
            success: function(response) {
                console.log(response); // 예: {"available": true}
                if (response.available) {
                    guideError.hide();
                    guideOk.show();
                    $("#idDuplicateCheck").val("1");
                } else {
                    guideError.show();
                    guideOk.hide();
                    $("#idDuplicateCheck").val("0");
                }
            }
        });
    });
});

//주소검색 api

const btn = document.querySelector("#btn");
btn.addEventListener("click", () => {
    new daum.Postcode({
        oncomplete: function(data) {
            console.log(data);

            let fullAddr = '';
            let extraAddr = '';

            if(data.userSelectedType === 'R') {
                fullAddr = data.roadAddress;
            } else {
                fullAddr = data.jibunAddress;
            }

            if(data.userSelectedType == 'R') {
                if(data.bname !== '') {
                    extraAddr += data.bname;
                }
                if(data.buildingName !== '') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                fullAddr += (extraAddr !== '' ? ' (' + extraAddr + ')' : '');
            }

            document.memberCreateFrm.memberAddr.value = fullAddr;

        }
    }).open();
});

