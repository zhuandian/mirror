import React, {Component} from 'react';
import './App.css';

class App extends Component {


    constructor(props) {
        super(props);
        this.state = {
            code: 0,
            contentEntity: {},
            open: false
        }
    }

    componentDidMount() {

        let that = this
        setInterval(function () {
            let query = window.bmob.Query("SendEntity");
            query.find().then((sendEntity) => {


                if (that.state.code < sendEntity[0].code || sendEntity[0].open == true) {

                    window.bmob.Query("ContentEntity").get(sendEntity[0].contentEntity.objectId)
                        .then((res) => {
                            that.setState({
                                contentEntity: res
                            })
                            console.log(res);
                        })
                }
                that.setState({
                    code: sendEntity[0].code,
                    open: sendEntity[0].open

                })

            })
        }, 2000)
    }

    render() {
        let {contentEntity, open} = this.state

        return (
            <div className="App">

                {
                    open ?
                        <div>

                            {contentEntity.type == 1 ? <img src={contentEntity.content}/> : ""}

                            {contentEntity.type == 2 ? <span>{contentEntity.content}</span> : ""}

                            {contentEntity.type == 3 ?
                                <video src={contentEntity.content} controls="autoplay" autoPlay="autoplay" width="100%"
                                       height="100%"/> : ""}
                        </div>
                        :
                        <span>
                            投影未开始
                        </span>

                }


            </div>
        );
    }


}

export default App;
