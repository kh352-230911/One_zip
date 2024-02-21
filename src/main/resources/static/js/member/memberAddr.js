document.addEventListener('DOMContentLoaded', function() {
    const btn = document.querySelector("#addrBtn");
    if (btn) {
        btn.addEventListener("click", function() {
            new daum.Postcode({
                oncomplete: function(data) {
                    console.log(data);

                    let fullAddr = '';
                    let extraAddr = '';

                    if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                        fullAddr = data.roadAddress;
                    } else { // 사용자가 지번 주소를 선택했을 경우
                        fullAddr = data.jibunAddress;
                    }

                    // 사용자가 도로명 주소를 선택하고, 건물명이 있는 경우 추가 정보를 구성
                    if (data.userSelectedType === 'R' && data.bname !== '') {
                        extraAddr += data.bname;
                        if (data.buildingName !== '') { // 건물명이 추가로 있으면 이를 추가
                            extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                        }
                        fullAddr += (extraAddr !== '' ? ' (' + extraAddr + ')' : '');
                    }

                    // 최종적으로 구성된 주소 정보를 입력 폼에 설정
                    document.memberCreateFrm.memberAddr.value = fullAddr;
                }
            }).open();
        });
    } else {
        console.error("#addrBtn not found");
    }
});
