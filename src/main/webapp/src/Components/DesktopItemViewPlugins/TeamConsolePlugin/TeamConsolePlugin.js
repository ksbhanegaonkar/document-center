import React,{Component} from 'react';
import './TeamConsolePlugin.css';
import {getRequest,postRequest} from '../../Utils/RestUtil';

class TeamConsolePlugin extends Component{

    state={
      teamName:"",
      teamDL:"",
        successMsg:'',
        errorMsg:'',
        allUsers:[]
    }
  componentDidMount(){
    this.fetchAllUsers();
  }
    render() {


            return (<div className='team-console'>
                        <label className="team-console-label">Team Name :</label>
                        <input className="teamuser-console-input" type="text" onChange={this.setTeamName.bind(this)} placeholder="User Name" name="username" required/>
                        <br></br>
                        <label className="team-console-label">Team DL :</label>
                        <input className="team-console-input" type="text" onChange={this.setTeamDL.bind(this)} placeholder="Enter Email" name="email" required/>
                        <br></br>
                        <button onClick={this.addTeam.bind(this)}>Add Team</button>
                        <br></br>
                        <div className="multiple-team-manager-select">

                        <label className="team-console-label">Team Managers :</label>
                        <br></br>
                            <select id="all-users" name="all-users" multiple>
                              {this.state.allUsers.map(u=>{
                                return (<option value={u}>{u}</option>);
                              })}
                            </select>

                        </div>
    
                        <div className="team-added-success-message">
                            <span>{this.state.successMsg}</span>
                        </div>
                         <div className="team-added-error-message">
                            <span>{this.state.errorMsg}</span>
                        </div>
                     </div>);
        }

    
        setTeamName(e){
            this.setState({teamName:e.target.value});
        }

        setTeamDL(e){
            this.setState({teamDL:e.target.value});
        }

        addTeam(){
            if(this.state.teamName===""){
                this.setState({errorMsg:"Please add team name...!",successMsg:""});
            }else if(this.state.teamdl===""){
                this.setState({errorMsg:"Please add team DL...!",successMsg:""});
            }else{
            postRequest("/addteam",{teamname:this.state.teamName,teamdl:this.state.teamDL},
            (data)=>{
                this.setState({successMsg:"Team added successfully...!",errorMsg:""});
            });
         }
           
        }

        fetchAllUsers(){
          getRequest("/getallusers",(data)=>this.setState({allUsers:data}));
        }


}
export default TeamConsolePlugin;


