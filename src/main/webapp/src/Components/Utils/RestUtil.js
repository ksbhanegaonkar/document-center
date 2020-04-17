export const baseUrl = "http://localhost:8083/services";
export const authBaseUrl = "http://localhost:8083/authenticate";
export const passwordResetUrl = "http://localhost:8083/resetpassword";
export const postRequest =(action,data,onDataReceive) =>{
    fetch(new Request(baseUrl+action),
    {
      headers:{
        'Content-Type': 'application/json',
       // ,'Access-Control-Allow-Origin':"*",
        'Authorization':localStorage.getItem("jwtToken")
      },
       method: 'POST', // or 'PUT'
       //mode:"no-cors",
       body: JSON.stringify(data) // data can be `string` or {object}!
      
    }
       )

  .then((res)=>res.json())
  .then(data=>{
    onDataReceive(data);
  });
}

export const downloadFilePostRequest =(action,data,onDataReceive) =>{
  fetch(new Request(baseUrl+action),
  {
    headers:{
      'Authorization':localStorage.getItem("jwtToken"),
      'Content-Type': 'application/json'
    },
     method: 'POST', // or 'PUT'
     //mode:"no-cors",
     body: JSON.stringify(data) // data can be `string` or {object}!
  }
     )
.then((res)=>res)
.then(data=>{
  onDataReceive(data);
});
}

export const uploadFilePostRequest =(action,data,onDataReceive) =>{
  fetch(new Request(baseUrl+action),
  {
    headers:{
      'Authorization':localStorage.getItem("jwtToken"),
      
    },
     method: 'POST', // or 'PUT'
     //mode:"no-cors",
     body: data // data can be `string` or {object}!
  }
     )
.then((res)=>res)
.then(data=>{
  onDataReceive(data);
});
}


export const getRequest =(action,onDataReceive) =>{
  fetch(new Request(baseUrl+action),
  {
    headers:{
      'Authorization':localStorage.getItem("jwtToken")
    },
     method: 'GET', // or 'PUT'    
  }
     )
.then((res)=>res.json())
.then(data=>{
  onDataReceive(data);
});
}

export const authPostRequest =(data,onDataReceive) =>{
  fetch(new Request(authBaseUrl),
  {
    headers:{
      'Content-Type': 'application/json',
     // ,'Access-Control-Allow-Origin':"*",
      'Authorization':localStorage.getItem("jwtToken")
    },
     method: 'POST', // or 'PUT'
     //mode:"no-cors",
     body: JSON.stringify(data) // data can be `string` or {object}!
    
  }
     )

.then((res)=>res.json())
.then(data=>{
  onDataReceive(data);
});
}


export const passwordResetPostRequest =(data,onDataReceive) =>{
  fetch(new Request(passwordResetUrl),
  {
    headers:{
      'Content-Type': 'application/json',
     // ,'Access-Control-Allow-Origin':"*",
      'Authorization':localStorage.getItem("jwtToken")
    },
     method: 'POST', // or 'PUT'
     //mode:"no-cors",
     body: JSON.stringify(data) // data can be `string` or {object}!
    
  }
     )

.then((res)=>res.json())
.then(data=>{
  onDataReceive(data);
});
}