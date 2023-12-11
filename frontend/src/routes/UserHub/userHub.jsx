import { Layout } from "antd";
import {
  UserOutlined,
  LaptopOutlined,
  NotificationOutlined,
} from "@ant-design/icons";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import "./userHub.css";
import React from "react";
import SideBar from "./components/SideBar";
import NavBar from "../../Components/NavBar/Navbar";
import Contenttest from "./components/Content";
import Projectes from "./components/Projects";

const { Header, Content, Footer, Sider } = Layout;

const UserHub = () => {
  return (
      <Layout style={{ background: "none" }}>
        <NavBar />
        <Layout style={{ background: "none" }}>
          <Sider style={{ background: "none" }}>
            <SideBar />
          </Sider>
          <Layout style={{ background: "none" }}>
            <Routes>
              <Route path="projetos/perfil" element={<Contenttest />} />
              <Route path="projetos/anot" element={<Projectes />} />
            </Routes>
          </Layout>
        </Layout>
      </Layout>
  );
};

export default UserHub;
