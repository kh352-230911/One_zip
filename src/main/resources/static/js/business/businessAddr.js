const btn1 = document.querySelector("#btn1");
btn1.addEventListener("click", () => {
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

            document.bizMemberCreateFrm.bizAddr.value = fullAddr;

        }
    }).open();
});

