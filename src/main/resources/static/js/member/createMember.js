$(document).ready(function() {
    // 페이지 진입 시 "이 아이디는 사용가능합니다."와 "이 아이디는 사용할 수 없습니다." 메세지 숨김처리
    $(".guide.ok, .guide.error").hide();
});

document.querySelector("#passwordConfirmation").onblur = (e) => {
    const password = document.querySelector("#password");
    const passwordConfirmation = e.target;
    if (password.value !== passwordConfirmation.value) {
        alert("패스워드가 일치하지 않습니다.");
        password.select();
    }
};

// 폼 제출 시 유효성 검사
document.memberCreateFrm.onsubmit = (e) => {
    const frm = e.target;
    const memberId = frm.memberId;
    const idDuplicateCheck = frm.idDuplicateCheck;
    const password = frm.password;
    const passwordConfirmation = frm.passwordConfirmation;
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
};

// 아이디 중복 검사
document.querySelector("#memberId").onkeyup = (e) => {
    const memberId = e.target;
    const guideOk = document.querySelector(".guide.ok");
    const guideError = document.querySelector(".guide.error");
    const idDuplicateCheck = document.querySelector("#idDuplicateCheck");

    if (!/^\w{4,}$/.test(memberId.value.trim())) {
        guideError.style.display = "none";
        guideOk.style.display = "none";
        idDuplicateCheck.value = 0
        return;
    }

    $.ajax({
        url: `${contextPath}member/checkIdDuplicate.do`,
        method: 'post',
        headers: {
            [csrfHeaderName]: csrfToken
        },
        data: {
            memberId: memberId.value.trim()
        },
        success(response) {
            console.log(response); // {"available" : true}
            const {available} = response;
            if (available) {
                guideError.style.display = "none";
                guideOk.style.display = "inline";
                idDuplicateCheck.value = 1;
            } else {
                guideError.style.display = "inline";
                guideOk.style.display = "none";
                idDuplicateCheck.value = 0;
            }
        }
    });

};

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
