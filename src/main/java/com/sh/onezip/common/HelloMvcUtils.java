package com.sh.onezip.common;

public class HelloMvcUtils {

    static String previous = """
    <li class="page-item mr-2">
        <a class="page-link text-gray-700 hover:text-blue-600" href="%s">Previous</a>
    </li>
    """;
    static String previousDisabled = """
    <li class="page-item disabled mr-2">
        <a class="page-link text-gray-400 cursor-not-allowed">Previous</a>
    </li>
    """;
    static String currentPageNo = """
    <li class="page-item active mr-2 border border-r-0 border-gray-300">
        <a class="page-link bg-blue-500 text-white" href="%s">%d</a>
    </li>
    """;
    static String notCurrentPageNo = """
    <li class="page-item mr-2 border border-r-0 border-gray-300">
        <a class="page-link text-gray-700 hover:text-blue-600" href="%s">%d</a>
    </li>
    """;
    static String next = """
    <li class="page-item mr-2">
        <a class="page-link text-gray-700 hover:text-blue-600" href="%s">Next</a>
    </li>
    """;
    static String nextDisabled = """
    <li class="page-item disabled mr-2">
        <a class="page-link text-gray-400 cursor-not-allowed">Next</a>
    </li>
    """;


    /**
     *
     *  - page 현재페이지
     *  - limit 한페이지당 표시할 개체수
     *  - totalCount 전체 개체수
     *  - totalPage 전체페이지수
     *  - pagebarSize 페이지바의 숫자개수
     *  - pageNo 페이지 증감변수
     *  - pagebarStart | pagebarEnd 페이지 증감변수의 범위
     *  - url 요청 url
     * @param limit
     * @param totalCount
     * @param url
     * @return
     */
    // realPage: 실제 보이는 페이지 번호
    // url은 pageabel의 page(realPage - 1)를 따라야 하고,
    // 사용자에게 보이는 realPage는 1이어야 한다.
    public static String getPagebar(int page, int limit, int totalCount, String url) {
        StringBuilder pagebar = new StringBuilder();
        // /mvc/admin/memberList
        // /mvc/admin/searchMember?search-type=xxx&search-keyword=yyyy
        //http://localhost:8080/mvc/admin/memberList?search-type=id&search-keyword=a&page=2
        url += (url.contains("?")) ? "&page=" : "?page=" ;
        // 전체페이지수
        int totalPage = (int) Math.ceil((double) totalCount / limit);
        int pagebarSize = 5;
        // 1 2 3 4 5
        // 6 7 8 9 10
        // 11 12 13 14 15
        int pagebarStart = (page - 1) / pagebarSize * pagebarSize + 1;
        int pagebarEnd = pagebarStart + pagebarSize - 1;
        int pageNo = pagebarStart;

        // 1. 이전
        if(pageNo == 1) {
            // 비활성화 이전
            pagebar.append(previousDisabled);
        }
        else {
            // 활성화 이전
//            """
//        <li>
//          <a href="%s" class="flex items-center justify-center px-3 h-8 ms-0 leading-tight text-gray-500 bg-white border border-e-0 border-gray-300 rounded-s-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">
//            <span class="sr-only">Previous</span>
//            <svg class="w-2.5 h-2.5 rtl:rotate-180" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 6 10">
//              <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 1 1 5l4 4"/>
//            </svg>
//          </a>
//        </li>
//        """;에서 %s에 url + (pageNo - 1)값을 대입. 즉, 링크를 설정한 url로 변경하는 작업. ex)/mvc/admin/memberList?page=5
            pagebar.append(previous.formatted(url + (pageNo - 1))); //
        }

        // 2. 페이지넘버
        while(pageNo <= pagebarEnd && pageNo <= totalPage){
            if(pageNo == page) {
                // 현재페이지
                pagebar.append(currentPageNo.formatted(url + pageNo, pageNo));
            }
            else {
                // 현재페이지가 아닌 페이지
                pagebar.append(notCurrentPageNo.formatted(url + pageNo, pageNo));
            }
            pageNo++;
        }
        // 3. 다
        if(pageNo > totalPage) {
            // 비활성화 다음
            pagebar.append(nextDisabled);
        }
        else {
            // 활성화 다음
            pagebar.append(next.formatted(url + pageNo));
        }
        return pagebar.toString();
    }


}
