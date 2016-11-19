<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>

        <meta http-equiv="content-type" content="text/html; charset=UTF-8">    
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

        <link href="/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <link href="/css/style_loginForm.css" rel="stylesheet" type="text/css">
        <link href="/css/style.css" rel="stylesheet" type="text/css">

        <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>

        <link href="favicon.ico" rel="icon"/>
    </head>

    <body>
    <%@ include file="/html/header.jsp" %>
    
        <div class="content-wrapper">
            <div class="before-footer">

                <div id="loginModal" >
                    <div class="modal-dialog">
                        <div class="modal-content">

                            <div class="modal-header">
                                <h1 class="text-center">Sign in to CityCon</h1>
                            </div>

                            <div class="modal-body">
                                <form class="form col-md-12 center-block " method="post" action="/signin">
                                    <div class="form-group">
                                        <input type="text" class="form-control input-lg" placeholder="Login" name="login">
                                    </div>
                                    <div class="form-group">
                                        <input type="password" class="form-control input-lg" placeholder="Password" name="password">
                                    </div>
                                    <div class="form-group">
                                        <input type="submit" class="btn btn-primary btn-lg btn-block" value="Sign In">
                                    </div>
                                </form>
                            </div>


                            <!--TODO: без этого вся форма рушится -->
                            <div class="modal-footer">

                            </div>

                        </div>
                    </div>
                </div>


                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-footer">
                            <h4 class="pull-left" align="left">New to CityCon?</h4>
                            <h4><a class="pull-right" href="/signup">Create an account.</a></h4>
                        </div>
                    </div>
                </div>
            </div>

            <%@ include file="/html/footer.html" %>
        </div>
    </body>
</html>


