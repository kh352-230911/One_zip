// 이웃 삭제 함수
function deleteNeighbor(id) {
    // 이웃 삭제 요청을 보냄
    fetch('/neighbors/delete/' + id, {
        method: 'DELETE'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to delete neighbor');
            }
            // 성공적으로 삭제되었을 때 할 작업
            console.log('Neighbor successfully deleted');
            // 이웃 목록 갱신 또는 화면에서 삭제한 이웃 제거 등의 작업을 수행할 수 있습니다.
        })
        .catch(error => {
            console.error('Error deleting neighbor:', error);
            // 삭제 실패시 처리할 작업
        });
}