import {lazy} from 'react'

import {createBrowserRouter} from "react-router-dom";

const Home = lazy(() => import('../pages/Home'))
const Page1 = lazy(() => import('../pages/Page1'))
const Page2 = lazy(() => import('../pages/Page2'))

const Router = createBrowserRouter([
        {
            path: `/`,
            element: <Home/>,
            loader: async () => {
                return {}
            },
        }, {
            path: `page1`,
            element: <Page1/>,
            loader: async ({params}) => {
                return {}
            }
        }, {
            path: `page2`,
            element: <Page2/>,
            loader: async ({params}) => {
                return {}
            }
        }
    ]
)

export default Router;
