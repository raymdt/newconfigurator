xmlHttp = null;
var b = -10.312180;
var e = 10.659885;

function getXMLHttp() {
	if (window.XMLHttpRequest) {
		//alert(window.XMLHttpRequest);
		xmlHttp = new XMLHttpRequest();
		//alert('XMLHttpRequest');
	}
	else if (window.ActiveXObject) {
		//alert('activeXOBj');
		//alert(window.XMLHttpRequest);
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
}

function startRequest(x, y, id, rl) {
	getXMLHttp();
    //alert(typeof x);
   // alert(typeof y);
	var url="http://localhost:8080/AxisWS/services/Compute?wsdl";
	var envelope =
	'<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ser="http://wtp">' +
		'<soap:Header/>' +
		'<soap:Body>' +
			'<ser:add>' +
				'<ser:param0>' + x + '</ser:param0>' +
				'<ser:param1>' + y + '</ser:param1>' +
			'</ser:add>' +
		'</soap:Body>' +
	'</soap:Envelope>';

	var len = envelope.length;

	if (xmlHttp != null) {
		xmlHttp.onreadystatechange = function () {
                    //alert("Beginn ...");
			if( xmlHttp.readyState == 4 ) {
				//alert("... xmlHttp.readyState = "+xmlHttp.readyState);
				//alert("... xmlHttp.status = "+xmlHttp.status);
				if(xmlHttp.status == 200) {
					
					var response = xmlHttp.responseText;
					//alert("... xmlHttp.status = "+xmlHttp.status);
					//alert("... xmlHttp.responseText = "+response);
					
					//var xmlDoc=document.implementation.createDocument("","",null);
					//xmlDoc.load(response);
					
					var parser = new DOMParser();
					var xmlDoc=parser.parseFromString(response ,"text/xml");
					var res = xmlDoc.getElementsByTagName("return")[0].childNodes[0].nodeValue;
					//alert("... 22222222222222  + " );
					//alert("... return value" + res );
                                        
					var transform = document.getElementById(id);
                    
                    //#####################
                    //var camera1 = document.getElementById(id);
                    //var position = camera .getAttribute("position");
                    //var result = position.split(" ");
                    //var x = parseFloat(result[0]);
                    //var y = parseFloat(result[1]);
                    //var z = parseFloat(result[2]);
                    //#####################
                    
                    if(transform.getAttribute('id') == "Camera.001"){
                        if(rl == "l")
                            b += parseInt(res)*0.3; // parseFloat not ParseInt
                        if(rl == "r")
                            b -= parseInt(res)*0.3;
                        transform.setAttribute("position", b+" -5.518037 2.737089");
                        
                    }
                    
                    if(transform.getAttribute('id') == "Camera"){ // getAttribut wrong
                        if(rl == "l")
                            e += parseInt(res)*0.3;
                        if(rl == "r")
                            e -= parseInt(res)*0.3;
                        transform.setAttribute("rotation", b+" -5.390353 2.847347");
                    }
                    
                    //10.659885 -5.390353 2.847347
                    
                    //0.461173 0.88726 -0.009466 0.02262
                    //0.005703 0.999983 0.000759 0.006801
					//document.getElementById(id).innerHTML = res;
				} 
				else {
					document.getElementById("result").innerHTML = "Error";
				}
			} 
			else {
				//alert("readState = "+xmlHttp.readyState);
				//alert("status = "+xmlHttp.status);
				document.getElementById("result").innerHTML = "Working...";
			}
		}
		xmlHttp.open("POST", url, true);
		xmlHttp.setRequestHeader("Content-Type", "application/soap+xml;charset=UTF-8;action=\"urn:add\"");
		xmlHttp.setRequestHeader("Content-Length", len);

		xmlHttp.send(envelope);
	} 
	else {
		alert("Your browser does not support XMLHTTP!");
	}
}

