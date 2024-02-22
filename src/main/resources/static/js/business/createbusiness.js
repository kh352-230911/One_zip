$(document).ready(function() {
    // 페이지 진입 시 "이 아이디는 사용가능합니다."와 "이 아이디는 사용할 수 없습니다." 메세지 숨김처리
    $(".biz.guide.ok, .biz.guide.error").hide();
});

document.querySelector("#bizpasswordConfirmation").onblur = (e) => {
    const bizPassword = document.querySelector("#bizPassword");
    const bizpasswordConfirmation = e.target;
    if (bizPassword.value !== bizpasswordConfirmation.value) {
        alert("패스워드가 일치하지 않습니다.");
        bizPassword.select();
    }
};

// 폼 제출 시 유효성 검사
document.bizMemberCreateFrm.onsubmit = (e) => {
    const frm = e.target;
    const bizMemberId = frm.bizMemberId;
    const bizMemberIdDuplicateCheck = frm.bizMemberIdDuplicateCheck;
    const bizPassword = frm.bizPassword;
    const bizpasswordConfirmation = frm.bizpasswordConfirmation;
    const bizName = $("#bizName").val();

    if (!/^\w{4,}$/.test(bizMemberId)) {
        alert("아이디는 영문자, 숫자, _ 4자리이상 입력하세요.");
        $("#bizMemberId").select();
        e.preventDefault(); // 폼 제출 방지
        return false;
    }
    if (bizMemberIdDuplicateCheck == "0") {
        alert("사용 가능한 아이디를 입력해주세요.");
        $("#bizMemberId").select();
        e.preventDefault(); // 폼 제출 방지
        return false;
    }
    if (bizPassword !== bizpasswordConfirmation) {
        alert("패스워드가 일치하지 않습니다.");
        $("#bizPassword").select();
        e.preventDefault(); // 폼 제출 방지
        return false;
    }
    if (!/^[\w가-힣]{2,}$/.test(bizName)) {
        alert("이름을 2글자 이상 입력하세요.");
        $("#bizName").select();
        e.preventDefault(); // 폼 제출 방지
        return false;
    }
};

// 아이디 중복 검사
document.querySelector("#bizMemberId").onkeyup = (e) => {
    const bizMemberId = e.target;
    const BizguideOk = document.querySelector(".biz.guide.ok");
    const BizguideError = document.querySelector(".biz.guide.error");
    const bizMemberIdDuplicateCheck = document.querySelector("#bizMemberIdDuplicateCheck");

    if (!/^\w{4,}$/.test(bizMemberId.value.trim())) {
        BizguideError.style.display = "none";
        BizguideOk.style.display = "none";
        bizMemberIdDuplicateCheck.value = 0
        return;
    }

    $.ajax({
        url: `${contextPath}business/checkIdDuplicate.do`,
        method: 'post',
        headers: {
            [csrfHeaderName]: csrfToken
        },
        data: {
            bizMemberId: bizMemberId.value.trim()
        },
        success(response) {
            console.log(response); // {"available" : true}
            const {available} = response;
            if (available) {
                BizguideError.style.display = "none";
                BizguideOk.style.display = "inline";
                bizMemberIdDuplicateCheck.value = 1;
            } else {
                BizguideError.style.display = "inline";
                BizguideOk.style.display = "none";
                bizMemberIdDuplicateCheck.value = 0;
            }
        }
    });

};

//주소검색 api

//
// const btn = document.querySelector("#btn");
// btn.addEventListener("click", () => {
//     new daum.Postcode({
//         oncomplete: function(data) {
//             console.log(data);
//
//             let fullAddr = '';
//             let extraAddr = '';
//
//             if(data.userSelectedType === 'R') {
//                 fullAddr = data.roadAddress;
//             } else {
//                 fullAddr = data.jibunAddress;
//             }
//
//             if(data.userSelectedType == 'R') {
//                 if(data.bname !== '') {
//                     extraAddr += data.bname;
//                 }
//                 if(data.buildingName !== '') {
//                     extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
//                 }
//                 fullAddr += (extraAddr !== '' ? ' (' + extraAddr + ')' : '');
//             }
//
//             document.memberCreateFrm.memberAddr.value = fullAddr;
//
//         }
//     }).open();
// });}