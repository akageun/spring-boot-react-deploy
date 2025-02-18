import {ServerMap} from "@pinpoint-fe/server-map";
import {useState} from "react";

const Page1 = () => {
    const [data] = useState({
        nodes: [{id: '1', label: 'test'}, {id: '2', label: 'test2'}],
        edges: [{id: 'e1', source: '1', target: '2'}]
    });
    return (
        <div className="App">

            <ServerMap
                data={data}
                baseNodeId={'MY-APP'}
            />

        </div>
    );
}

export default Page1;
