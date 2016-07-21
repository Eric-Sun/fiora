<!DOCTYPE html>
<html class="bg-black">
<head>
    <meta charset="UTF-8">
    <title>AdminLTE | Log in</title>
    <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
    <link href="/static/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <!-- font Awesome -->
    <link href="/static/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
    <!-- Theme style -->
    <link href="/static/css/AdminLTE.css" rel="stylesheet" type="text/css"/>

</head>
<body class="bg-black">

<div class="form-box" id="login-box">
    <div class="header">Sign In</div>
    <form action="/j_spring_security_check" method="post">
        <div class="body bg-gray">
            <div class="form-group">
                <input type="text" name="j_username" class="form-control" placeholder="User ID"/>
            </div>
            <div class="form-group">
                <input type="password" name="j_password" class="form-control" placeholder="Password"/>
            </div>
            <div class="form-group">
                <input type="checkbox" name="remember_me"/> Remember me
            </div>
        </div>
        <div class="footer">
            <button type="submit" class="btn bg-olive btn-block">Sign me in</button>

            <p><a href="#">I forgot my password</a></p>

        </div>
    </form>

</div>


<!-- Bootstrap -->
<script src="../../js/bootstrap.min.js" type="text/javascript"></script>

</body>
</html>