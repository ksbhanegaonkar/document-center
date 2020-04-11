import React,{Component} from 'react';
import './RenameScreen.scss';
class RenameScreen extends Component{

    state={
        newName:""
    };

    render() {
        var style={
          display:this.props.isRename?'block':'none',
        };

        return (<div className="rename-screen" style={style}>
            <div className="rename-pannel">
                <input type="text" value={this.state.newName}></input>
                <button onClick={this.rename.bind(this)}>Rename</button>
            </div>

        </div>)
      }


      componentDidMount(){

        }
    

      rename(){
            this.props.doneRename();
        }

        componentWillUpdate(){
            // const [app, type, id, name] = this.props.appToRename.split("/");
            // console.log("name is ::::  "+name);
            // //this.state.newName=name;
        }


}
export default RenameScreen;