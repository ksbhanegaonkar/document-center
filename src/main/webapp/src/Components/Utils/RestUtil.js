export const postRequest =(action,data,onDataReceive) =>{
    fetch(new Request("http://localhost:8083"+action),
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
  fetch(new Request("http://localhost:8083"+action),
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
  fetch(new Request("http://localhost:8083"+action),
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
  fetch(new Request("http://localhost:8083"+action),
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