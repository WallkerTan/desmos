import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import "antd/dist/reset.css"; // Supports weights 200-700

import "@fontsource-variable/oswald/wght.css";

import { RouterProvider } from "react-router-dom";
import { Provider } from "react-redux";
import { Store } from "./store/Store";
import Router_Desmos from "./router/RouterDesmos";

createRoot(document.getElementById("root")!).render(
    <StrictMode>
        <Provider store={Store}>
            <RouterProvider router={Router_Desmos} />
        </Provider>
    </StrictMode>,
);
