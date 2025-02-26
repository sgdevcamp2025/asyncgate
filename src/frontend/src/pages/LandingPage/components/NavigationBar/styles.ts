import styled from 'styled-components';

export const NavBarContainer = styled.nav`
  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 90%;
  max-width: 120rem;
  min-height: 8rem;
  margin: 0 auto;
`;

export const Logo = styled.div`
  cursor: pointer;
  display: flex;
  align-items: center;
  height: 4rem;
`;

export const MenuContainer = styled.ul`
  display: flex;
  list-style: none;
`;

export const MenuItem = styled.li`
  margin: 0 0.7rem;
  padding: 1rem;

  & > a {
    font-size: 1.6rem;
    font-weight: 600;
    color: ${({ theme }) => theme.colors.white};
    text-decoration: none;

    &:hover {
      text-decoration: underline;
    }
  }
`;

export const LoginButton = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 7rem;
  height: 3.8rem;
  margin-left: 5rem;
  border-radius: 4rem;

  font-size: 1.4rem;
  font-weight: 700;
  color: ${({ theme }) => theme.colors.black};

  background-color: ${({ theme }) => theme.colors.white};
`;
