document.addEventListener('DOMContentLoaded', function() {
    const memberCreateForm = document.forms['memberCreateFrm'];

    memberCreateForm.addEventListener('submit', function(event) {
        // 아이디 검증
        const memberId = document.getElementById('memberId').value;
        if (!validateMemberId(memberId)) {
            alert('아이디는 최소 4글자 이상이어야 합니다.');
            event.preventDefault(); // 폼 제출 중단
            return false;
        }

        // 비밀번호 검증
        const password = document.getElementById('password').value;
        if (!validatePassword(password)) {
            alert('비밀번호를 확인해주세요. (검증 조건에 맞게 메시지 수정 필요)');
            event.preventDefault();
            return false;
        }

        // 전화번호 검증
        const phone = document.getElementById('phone').value;
        if (!validatePhone(phone)) {
            alert('전화번호는 숫자만 10~11자리 입력해야 합니다.');
            event.preventDefault();
            return false;
        }

        // 모든 검증 통과 후 폼 제출
        return true;
    });

    function validateMemberId(memberId) {
        return memberId.length >= 4;
    }

    function validatePassword(password) {
        // 여기에 비밀번호 복잡성 검증 로직 추가 가능
        return password.length >= 6; // 예시: 최소 6자 이상이어야 함
    }

    function validatePhone(phone) {
        return /^\d{10,11}$/.test(phone); // 숫자만 10자리 또는 11자리
    }
});