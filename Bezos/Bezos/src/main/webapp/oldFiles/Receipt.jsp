<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.app.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bezos - Payment</title>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Epilogue:wght@400&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Jockey+One&display=swap" rel="stylesheet">
    <style>
       
        body {
            margin: 0;
            padding: 0;
            font-family: 'Epilogue', sans-serif;
            display: flex;
            flex-direction: column;
            height: 100vh;
            background-color: #2B67F6;
            color: white;
        }

        header {
            background-color: #fff;
            color: #000;
            text-align: center;
            font-size: 24px;
            font-family: 'Jockey One', sans-serif;
        }

        header h1 {
            margin: 0;
            padding: 20px 0;
        }

            .back-button {
	background-color: #000000;
	color: white;
	padding: 10px 15px;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	position: absolute;
	top: 7%;
	left: 3%;
}
        .container {
            display: flex;
            justify-content: space-around;
            align-items: center;
            margin: auto;
            padding: 40px;
            background-color: #0056b3;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 80%;
            max-width: 900px;
        }

        .section {
            flex: 1;
            margin: 0 20px;
        }

        .divider {
            border-left: 2px solid rgb(0, 0, 0);
            height: 100%;
        }

     

        input[type=text] {
            width: calc(100% - 20px);
            padding: 10px;
            margin: 10px 0;
            border: none;
            border-radius: 5px;
        }

        .submit-btn {
            background-color: #000000;
            color: white;
            padding: 10px 30px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            display: block;
            width: 100px;
            margin: 20px auto;
        }
        

   h2 {
            font-size: 28px;
            margin-bottom: 15px;
        }
        .submit-btn:hover {
            background-color: #333333;
        }

        @media (max-width: 768px) {
            .container {
                flex-direction: column;
                padding: 20px;
            }

            .divider {
                border-left: none;
                border-top: 2px solid rgb(0, 0, 0);
                width: 100%;
                height: auto;
                margin: 20px 0;
            }

            .section {
                margin: 0;
            }
        }
        
    
        
    </style>
</head>
<body>
    <header>
        <h1>BEZOS</h1>
        <button onclick="window.location.href='AuctionEnded.jsp'" class="back-button">BACK</button>
    </header>
    <form action="shipping.jsp" method="post">
    
    <div class="container">
        <div class="section">
            <h2>Winning Bidder</h2>
            <% 
                User winnerDetails = (User) session.getAttribute("winnerDetails");
                Double totalCost = (Double) session.getAttribute("totalCost");
            %>
    
            <label for="fname">First Name:</label>
            <input type="text" id="fname" name="fname" value="<%= winnerDetails != null ? winnerDetails.getFirstName() : "" %>" readonly>
            <label for="lname">Last Name:</label>
            <input type="text" id="lname" name="lname" value="<%= winnerDetails != null ? winnerDetails.getLastName() : "" %>" readonly>
            <label for="address">Street address:</label>
            <input type="text" id="address" name="address" value="<%= winnerDetails != null ? winnerDetails.getAddress() : "" %>" readonly>
            <label for="province">Province:</label>
            <input type="text" id="province" name="province" value="<%= winnerDetails != null ? winnerDetails.getCity() : "" %>" readonly>
            <label for="country">Country:</label>
            <input type="text" id="country" name="country" value="<%= winnerDetails != null ? winnerDetails.getCountry() : "" %>" readonly>
            <label for="postal">Postal Code:</label>
            <input type="text" id="postal" name="postal" value="<%= winnerDetails != null ? winnerDetails.getPostalCode() : "" %>" readonly>
            <label for="total">Total Cost:</label>
            <input type="text" id="total" name="total" value="<%= totalCost != null ? totalCost.toString() : "" %>" readonly>
        </div>

        <div class="divider"></div>
        <div class="section">
           <h2>Credit Card</h2>
            <label for="cardnum">Card #:</label>
            <input type="text" id="cardnum" name="cardnum" required>
            <label for="cardname">Name on card:</label>
            <input type="text" id="cardname" name="cardname" required>
            <label for="expdate">Expiry Date:</label>
            <input type="text" id="expdate" name="expdate" required>
            <label for="cvv">Security Code:</label>
            <input type="text" id="cvv" name="cvv" required>
        <button type="submit" id="payButton" class="submit-btn">Submit</button>
    </div>
</div>

          
       
</form>


    <script>
  
   
</script>

    
</body>
</html>

