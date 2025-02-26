import * as S from './styles';

/**
 *
 * @example
 * ```tsx
 * <Toggle isOn={공개여부 상태값}
 *  onToggle={() => 해당 상태값 setter함수}
 * />
 * ```
 *
 * @param isOn - 토글 스위치 ON/OFF 상태입니다(공개/비공개)
 * @param onToggle - 실행 시 실행될 콜백 함수
 */
interface ToggleProps {
  isOn: boolean;
  onToggle: () => void;
}
const Toggle = ({ isOn, onToggle }: ToggleProps) => {
  return (
    <S.Toggle $isOn={isOn} onClick={onToggle}>
      <S.ToggleCircle
        animate={{
          x: isOn ? 25 : 0,
        }}
        transition={{
          type: 'spring',
          stiffness: 500,
          damping: 30,
        }}
      >
        <S.IconWrapper>{isOn ? '✓' : '✕'}</S.IconWrapper>
      </S.ToggleCircle>
    </S.Toggle>
  );
};

export default Toggle;
