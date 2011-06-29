		function changeView(){
                var transform = document.getElementById("xml3D");
                var activView = transform.getAttribute("activeView");
                if(activView == "#Camera.001"){
                  transform.setAttribute("activeView", "#Camera");
                }
                
                if(activView == "#Camera"){
                        transform.setAttribute("activeView", "#Camera.001");
                }
            }
