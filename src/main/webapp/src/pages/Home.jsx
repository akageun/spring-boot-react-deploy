import {useEffect, useState} from "react";
import {Link} from "react-router-dom";

const Home = () => {

    const [data, setData] = useState({});

    useEffect(() => {
        fetch('/api/test')
            .then((response) => response.json())
            .then((data) => setData(data))
            .catch(error => console.error(error))
        ;

    }, []);

    return (
        <div className="App">
            Test App <br/>

            <Link to={"/page1"}>Page1</Link> &nbsp; &nbsp;
            <Link to={"/page2"}>Page2</Link>

            <br/>

            <hr/>
            {JSON.stringify(data)}
        </div>
    );
}

export default Home;
