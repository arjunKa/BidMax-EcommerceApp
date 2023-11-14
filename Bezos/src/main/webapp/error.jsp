<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            color: #333;
            text-align: center;
            padding-top: 50px;
        }
        .error-container {
            background-color: #fff;
            max-width: 400px;
            padding: 20px;
            margin: 0 auto;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .error-message {
            color: #ff0000;
            margin: 20px 0;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <h1>Oops!</h1>
        <div class="error-message">
            <%
                String errorMessage = request.getParameter("message");
                if(errorMessage != null && !errorMessage.trim().isEmpty()) {
                    out.println(errorMessage);
                } else {
                    out.println("An unexpected error occurred.");
                }
            %>
        </div>
        <a href="Main.html">Go back to home page</a>
    </div>
</body>
</html>
