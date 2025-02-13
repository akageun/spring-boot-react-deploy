import './App.css';
import {useEffect, useState} from "react";

function App() {

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
            {JSON.stringify(data)}
        </div>
    );
}

export default App;
