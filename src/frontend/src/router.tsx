import { createBrowserRouter, Outlet } from 'react-router-dom';

import ModalRenderer from './components/common/ModalRender';
import AuthFullLayout from './components/layout/AuthFullLayout';
import FullLayout from './components/layout/FullLayout';
import PublicOnlyLayout from './components/layout/PublicOnlyLayout';
import FriendsPage from './pages/FriendsPage';
import LandingPage from './pages/LandingPage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';

const router = createBrowserRouter([
  {
    element: (
      <>
        <Outlet />
        <ModalRenderer />
      </>
    ),
    children: [
      {
        element: <FullLayout />,
        children: [
          {
            path: '/',
            element: <LandingPage />,
          },
        ],
      },
      {
        element: <PublicOnlyLayout />,
        children: [
          {
            path: '/login',
            element: <LoginPage />,
          },
          {
            path: '/register',
            element: <RegisterPage />,
          },
        ],
      },
      {
        element: <AuthFullLayout />,
        children: [
          {
            path: '/friends',
            element: <FriendsPage />,
          },
        ],
      },
    ],
  },
]);

export default router;
