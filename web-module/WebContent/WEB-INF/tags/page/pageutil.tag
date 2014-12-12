<%@ include file="/taglibs.jsp"%>
<%@ tag pageEncoding="UTF-8"%>
<%@ attribute name="link" required="true"%>
<%@ attribute name="pagerObj" required="true" type="com.acs.core.common.entity.SimplePager"%>
<div class="pagination pagination-centered">
	<ul>
		<li class="prev <c:if test="${pager.currentPage==0}">disabled</c:if>"><a href="${link}?pager.currentPage=${pager.currentPage-1}">Previous</a></li>
		<c:forEach begin="${pager.startPage}" end="${pager.endPage}" varStatus="row" var="page">
			<li<c:if test="${pager.currentPage == row.index-1}"> class="active"</c:if> ><a href="${link}?pager.currentPage=${page-1}">${page}</a></li>
		</c:forEach>
	 	<li class="next <c:if test="${pager.currentPage==pager.pageSize -1}">disabled</c:if>"><a href="${link}?pager.currentPage=${pager.currentPage + 1}">Next</a></li> 
	 	<li style="line-height:34px;">目前在第${pager.currentPage + 1 }页,资料共 ${pager.totalSize} 笔</li>
 	</ul>
</div>