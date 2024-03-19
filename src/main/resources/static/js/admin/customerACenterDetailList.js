// let previousUrl = window.location.href; // 현재 페이지 URL을 이전 페이지 URL로 설정
//
// function openEditModal(id) {
//     // 현재 답변 내용을 수정 폼에 채우기
//     document.getElementById("editedContent").value = document.getElementById("aoneContent").value;
//     // 모달 창 보이기
//     document.getElementById("editModal").style.display = "block";
//
//     // URL 변경
//     window.history.pushState({}, "", "/admin/customerACenterUpdateList.do");
// }
//
// // 수정 모달 창 닫기
// function closeEditModal(id) {
//     // 모달 창 감추기
//     document.getElementById("editModal").style.display = "none";
//
//     // URL 변경 (모달을 닫을 때 이전 URL로 변경)
//     window.history.pushState({}, "", previousUrl);
// }
// let urlParams = new URLSearchParams(window.location.search);
// let id = urlParams.get('id');

let previousUrl = window.location.href; // 현재 페이지 URL을 이전 페이지 URL로 설정

function openEditModal() {
    // 현재 답변 내용을 수정 폼에 채우기
    let editedContent = document.getElementById("editedContent");
    let aoneContent = document.getElementById("aoneContent").value;
    if (editedContent) {
        editedContent.value = aoneContent;
    }
    // 모달 창 보이기
    document.getElementById("editModal").style.display = "block";
}

// 수정 모달 창 닫기
function closeEditModal() {
    // 모달 창 감추기
    document.getElementById("editModal").style.display = "none";
}

let urlParams = new URLSearchParams(window.location.search);
let id = urlParams.get('id');
if (!id) {
    console.error("ID is undefined or null.");
}

document.getElementById("editModal").addEventListener("submit", function(event) {
    // 서버에 수정 내용 제출
    event.preventDefault(); // 기본 이벤트 동작 방지
    let editedContent = document.getElementById("editedContent").value;
    let formData = new FormData();
    formData.append("id", id);
    formData.append("newAoneContent", editedContent);

    fetch("/admin/customerACenterUpdateList.do", {
        method: "POST",
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then(data => {
            console.log("Success:", data);
            // 페이지 새로고침
            window.location.reload();
        })
        .catch(error => {
            console.error("Error:", error);
        });
});

function openEditModal() {
    // 현재 답변 내용을 수정 폼에 채우기
    let editedContent = document.getElementById("editedContent");
    let aoneContent = document.getElementById("aoneContent").value;
    if (editedContent) {
        editedContent.value = aoneContent;
    }
    // 모달 창 보이기
    document.getElementById("editModal").style.display = "block";

    // URL 변경
    window.history.pushState({}, "", "/admin/customerACenterUpdateList.do?id=" + id);
}

// 수정 모달 창 닫기
function closeEditModal() {
    // 모달 창 감추기
    document.getElementById("editModal").style.display = "none";

    // URL 변경 (모달을 닫을 때 이전 URL로 변경)
    window.history.pushState({}, "", previousUrl);
}

