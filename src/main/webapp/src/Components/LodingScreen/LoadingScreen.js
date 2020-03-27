import React,{Component} from 'react';
import './LoadingScreen.scss';
class LoadingScreen extends Component{

    render() {
        var style={
          display:this.props.isLoading?'block':'none'
        };
        return (<div className="loading-screen" style={style}>
          <p id="loading-text">Loading....</p>

        </div>)
      }


}
export default LoadingScreen;