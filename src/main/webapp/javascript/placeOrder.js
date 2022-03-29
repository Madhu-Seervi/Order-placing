if(window.sessionStorage.getItem("Login")=='true'){
	  
    function loadData(){
    	var userObject = window.localStorage.getItem("id");
    	var providerValue = JSON.parse(userObject);
    	var provider = document.getElementById("provider").value = providerValue.providerId + " " +providerValue.lastName + " " +providerValue.firstName;
    	ordertype();
    	patient();
    }
    
    function patient(){
    var patient = document.getElementById("patient");

    fetch("http://localhost:8080/order-registration/webapi/chart-order/patients", {
            method: "get",
            headers: new Headers({
              "Content-Type": "application/json", 
        }),
     })
         .then(function(response){
        	 console.log(response.status);
        	 var responseStatus = response.status;
        	 response.text().then(function(result){
        		 console.log(result);
                 if(result == "Unable to find patients in the system"){
                 	window.alert("Status code: "+responseStatus+"\n"+result);
                 }
                 else{
                 	var jsonresponse = JSON.parse(result); 
                 	jsonresponse.forEach((item,index) => {
                         var option = document.createElement("option");
                         option.text=item.patientId + " " + item.patientLastname + "," + item.patientFirstname;
                         patient.appendChild(option);
                     });
                 }
        	 });
         });
    }
	function ordertype(){
		
		var orderType = document.getElementsByName('ordertype');
		var orderTypeValue;
			for(var i = 0; i < orderType.length; i++){
					if(orderType[i].checked){
						orderTypeValue = orderType[i].value;
					break;
					}
			}
			console.log(orderTypeValue);
	   
	        var synonym=document.getElementById("synonym");
	        synonym.innerHTML="";
	            console.log(orderTypeValue);
	            fetch("http://localhost:8080/order-registration/webapi/chart-order/synonyms?orderType="+orderTypeValue, {
	                    method: "get",
	                    headers: new Headers({
	                      "Content-Type": "application/json",   
	                }),
	             })
	             .then(function(response){
	            	 console.log(response.status);
	            	 var responseStatus = response.status;
	            	 response.text().then(function(result){
	            		 console.log(result);
                         if(result == "No list of synonyms found" || result == "Invalid input"){
                         	window.alert("Status code: "+responseStatus+"\n"+result);
                         }
                         else{
                         	 var jsonresponse=JSON.parse(result);
	                            jsonresponse.forEach((item,index) => {
	                                var option = document.createElement("option");
	                                option.text=item.synonymId + " " +item.mnemonic;
	                                synonym.appendChild(option);
                              });
                          }
	            	 });
	             });
	}
	    
	function order(){
	    var provider = document.getElementById("provider").value.replace(/\D/g,'');
	    var patient = document.getElementById("patient").value.replace(/\D/g,'');
	    var encounter = document.getElementById("encounter").value;
	    var synonym = document.getElementById("synonym").value.replace(/\D/g,'');
	    var dose = document.getElementById("dose").value;
	    var duration = document.getElementById("duration").value;
	    var frequency = document.getElementById("frequency").value;

	    if(provider.length ==""|| patient.length == "" || encounter.length == "" || synonym.length == "" || dose.length == "" ||frequency.length == ""){
	        window.alert("Please fill all information");
	    }
	    else{
	    	 console.log(provider,patient,encounter,synonym,dose,duration,frequency);

	 	    var data={
	 	        providerId:provider,
	 	        patientId:patient,
	 	        encounter:encounter,
	 	        synonymId:synonym,
	 	        dose:dose,
	 	        duration:duration,
	 	        frequency:frequency,
	 	    };

	 	    fetch("http://localhost:8080/order-registration/webapi/chart-order/add-order",{
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
		 	            if(result == "Post request: null" || result == "Synonym Id: not a positive number" || result == "Patient Id: not a positive number" || result == "Provider Id: not a positive number" || result == "Encounter: null" || result == "Dose: Invalid input" || result == "Frequency: null" || result == "Order could not placed successfully" || result == "Order not placed" || result == "Database exception occured while trying to sign order."){
		 	            	 window.alert("Status code: "+responseStatus+"\n"+result);
		 	            }
		 	            else{
		 	            	window.alert("Order placed successful Order Id:" +result);
		 	            }
	        	 });
	         });
	 	}
	 }
	
	function logout(){
		window.sessionStorage.setItem("Login",null);
		window.location.href="http://localhost:8080/order-registration/login.html";
	}
}
else{
	window.alert("Please login");
	window.location.href="http://localhost:8080/order-registration/login.html";
}
