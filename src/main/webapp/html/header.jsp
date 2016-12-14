<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<header id="header">
	<div class="row">
		<div class="col-sm-5">
			<a href="/"><img alt="logo" id="menu" src="/img/logo.png"/></a>
		</div>
		<div class="col-sm-7" >
			<%--<c:if test="${showLogoutBtn}">--%>
                <a href='/logout' class='btn btn-header btn-default pull-right'>Logout</a>
            <%--</c:if>
            <c:if test="${showConnectionsBtn}">--%>
                <a href="/orders" class="btn btn-header btn-default pull-right">Orders</a>
				<a href="/carCrews" class="btn btn-header btn-default pull-right">CarCrews</a>

            <c:if test="${showUsersBtn}">
                <a href="/users" class="btn btn-header btn-default pull-right">Users</a>
			</c:if>
		</div>
	</div>
</header>