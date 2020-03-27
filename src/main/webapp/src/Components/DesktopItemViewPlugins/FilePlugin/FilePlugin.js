import React,{Component} from 'react';
import './FilePlugin.css';
import {getRequest,postRequest} from '../../Utils/RestUtil';
class FilePlugin extends Component{

  constructor(props) {
    super(props);
    //console.log("value is :::"+this.props.getPayload(this.props.item.appId));
    this.state = {value: this.props.payload};

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  componentDidMount(){
    this.getPayload(this.props.item.appId);
  }

  handleChange(event) {
    this.setState({value: event.target.value});
  }

  handleSubmit(event) {
    //alert('A name was submitted: ' + this.state.value);
    this.props.updatePayload(this.props.item.appId,this.state.value);
    event.preventDefault();
  }

  getPayload(appId){
    getRequest('/getapppayload/'+appId,(data)=>{
      console.log("payload is ::::::"+data.payload);
      this.setState({value:data.payload});
    });
    
  }


    render() {
      
        return <div className="FilePlugin">

        <form onSubmit={this.handleSubmit}>
    
                  <textarea rows="40" cols="100" type="text" value={this.state.value} onChange={this.handleChange} />
         
                <input type="submit" value="Save" />
        </form>

            
        </div>
      }



}
export default FilePlugin;


