import { Menu } from 'antd';
import React from 'react';
import './SideBar.css';
import { Link } from 'react-router-dom';

const SideBar = () => {
  return (
    <Menu theme="light" mode='inline' className='menu-bar'>
      <Menu.Item key="1"><Link to="/userhub/projetos/perfil">Meu Perfil</Link></Menu.Item>
      <Menu.Item key="2">Anotações</Menu.Item>
      <Menu.Item key="3">Diagramas</Menu.Item>
      <Menu.Item key="4">Repositorios</Menu.Item>
      <Menu.Item key="5">Carreira</Menu.Item>
    </Menu>
  );
};

export default SideBar;