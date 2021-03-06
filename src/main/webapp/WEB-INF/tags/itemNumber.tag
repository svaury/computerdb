<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="page" required="true" type="Object"
	description="search page"%>


<div class="btn-group btn-group-sm pull-right" id="itemNumber"
	role="group">
	<a
		href="<c:url value="/dashboard">
               <c:param name="itemNumber" value="10"/>
                  <c:if test ="${not empty page.search}">
               <c:param name="search" value="${page.search}"/>
					</c:if>
					<c:if test ="${not empty page.orderBy}">
               <c:param name="orderby" value="${page.orderBy}"/>
               <c:param name="sort" value="${page.sort}"/>
					</c:if>
        						 </c:url>">
		<button type="button" class="btn btn-default">10</button>
	</a> <a
		href="<c:url value="/dashboard">
               <c:param name="itemNumber" value="50"/>
                <c:if test ="${not empty page.search}">
               <c:param name="search" value="${page.search}"/>
					</c:if>
					<c:if test ="${not empty page.orderBy}">
               <c:param name="orderby" value="${page.orderBy}"/>
               <c:param name="sort" value="${page.sort}"/>
					</c:if>
               </c:url>">
		<button type="button" class="btn btn-default">50</button>
	</a> <a
		href="<c:url value="/dashboard">
               <c:param name="itemNumber" value="100"/>
                 <c:if test ="${not empty page.search}">
               <c:param name="search" value="${page.search}"/>
					</c:if>
					<c:if test ="${not empty page.orderBy}">
               <c:param name="orderby" value="${page.orderBy}"/>
               <c:param name="sort" value="${page.sort}"/>
					</c:if>
               </c:url>">
		<button type="button" class="btn btn-default" value="100">100</button>

	</a>
</div>