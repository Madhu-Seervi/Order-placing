function login(){
    var userId = document.getElementById("userId").value;
    var secretCode = document.getElementById("password").value;
    
    if(userId.length == "" || secretCode.length == ""){
        window.alert("Please enter username or password");
    }
    else{
    	console.log(userId);

        var data={
            providerId:userId,
            secretCode:secretCode,
        };
       
       fetch("http://localhost:8080/order-registration/webapi/chart-order/user", {
            method: "post",
            headers: new Headers({
              "Content-Type": "application/json", 
            }),
            body: JSON.stringify(data),
          })
        .then(function(response){
        	console.log(response.status);
        	var responseStatus = response.status;
        	response.text().then(function(result){
        		console.log(result);
        		if(result=="User not found" || result=="Unauthorized access" || result=="Incorrect password" || result == "Provider Id: not a positive number")
        		{
                	window.alert("Status code: "+responseStatus+"\n"+result);
                }
                else
                {
                	var jsonresponse = JSON.parse(result);
                	if(jsonresponse.providerId==userId )
                  {
                     	window.sessionStorage.setItem("Login",'true');
                     	window.localStorage.setItem("id",result);
                     	window.location.href="http://localhost:8080/order-registration/placeOrder.html";
                   }
                }
        	});
        });
    }
}