import {lazy, Suspense} from 'react'

import {createBrowserRouter} from "react-router-dom";

const Home = lazy(() => import('../pages/Home'))
const Page1 = lazy(() => import('../pages/Page1'))
const Page2 = lazy(() => import('../pages/Page2'))

const loading = () => {
    return (
        <div>
            Loading...
        </div>
    )
}

const Router = createBrowserRouter([
        {
            path: `/`,
            element: <Suspense fallback={loading()}><Home/></Suspense>,
            loader: async () => {
                return {}
            },
        }, {
            path: `page1`,
            element: <Suspense fallback={loading()}><Page1/></Suspense>,
            loader: async ({params}) => {
                return {}
            }
        }, {
            path: `page2`,
            element: <Suspense fallback={loading()}><Page2/></Suspense>,
            loader: async ({params}) => {
                return {}
            }
        }
    ]
)

export default Router;
