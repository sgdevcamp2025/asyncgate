import { createBrowserRouter } from 'react-router-dom';

import AuthFullLayout from './components/layout/AuthFullLayout';
import FullLayout from './components/layout/FullLayout';
import PublicOnlyLayout from './components/layout/PublicOnlyLayout';
import LandingPage from './pages/LandingPage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';

const router = createBrowserRouter([
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
    children: [],
  },
]);

export default router;
