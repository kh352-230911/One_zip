document.addEventListener("DOMContentLoaded", function() {
    // 모달 열기
    var neighborBtn = document.getElementById("neighborBtn");
    var neighborModal = document.getElementById("neighborModal");
    var closeBtn = document.querySelector(".close");

    neighborBtn.addEventListener("click", function () {
        neighborModal.style.display = "block";
        // 이웃 목록을 Ajax를 사용하여 가져오는 등의 동적 처리 코드 작성
        fetchNeighbors();
    });

    // 모달 닫기
    closeBtn.addEventListener("click", function () {
        neighborModal.style.display = "none";
    });

    // 모달 외부 클릭 시 닫기
    window.addEventListener("click", function (event) {
        if (event.target === neighborModal) {
            neighborModal.style.display = "none";
        }
    });
});
// Ajax를 통해 이웃 목록을 가져오고 테이블에 추가하는 함수
function fetchNeighbors() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/onezip/neighbors/list', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                var neighbors = JSON.parse(xhr.responseText);
                populateNeighborTable(neighbors); // 테이블에 데이터 추가
            } else {
                console.error('Error fetching neighbors:', xhr.statusText);
            }
        }
    };
    xhr.send();
}

// 이웃 목록을 테이블에 추가하는 함수
function populateNeighborTable(neighbors) {
    var tableBody = document.getElementById("neighborList");
    tableBody.innerHTML = ''; // 이전 데이터 초기화
    neighbors.forEach(function(neighbor) {
        var row = document.createElement('tr');
        row.innerHTML = `
            <td>${neighbor.id}</td>
            <td>${neighbor.member1.memberId}</td>
            <td>${neighbor.member2.memberId}</td>
            <td>${neighbor.status}</td>
            <td>${neighbor.requestedAt}</td>
            <td>${neighbor.acceptedAt}</td>
        `;
        tableBody.appendChild(row);
    });
}