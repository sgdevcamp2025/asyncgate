import * as S from './styles';

interface VideoCardProps {
  userId: string;
  stream?: MediaStream;
  localRef?: React.MutableRefObject<HTMLVideoElement | null>;
}

const VideoCard = ({ userId, stream, localRef }: VideoCardProps) => {
  return (
    <S.VideoCard>
      <S.UserName>{userId}</S.UserName>
      <S.Video
        autoPlay
        playsInline
        ref={(videoElement) => {
          if (localRef) {
            localRef.current = videoElement;
          }

          if (stream && videoElement && videoElement.srcObject !== stream) {
            videoElement.srcObject = stream;
          }
        }}
      />
    </S.VideoCard>
  );
};

export default VideoCard;
