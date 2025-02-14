import React, { useState } from 'react';

type UseFunnelProps<T extends string> = {
  defaultStep: T;
  stepList: T[];
};

type StepProps<T extends string> = {
  children: React.ReactNode;
  name: T;
};

type FunnelProps<T extends string> = {
  step: T;
  children: React.ReactElement<StepProps<T>>[];
};

const Step = <T extends string>(stepProps: StepProps<T>) => {
  return <>{stepProps.children}</>;
};

const Funnel = <T extends string>({ children, step }: FunnelProps<T>) => {
  const targetStep = children.find((curStep) => curStep.props.name === step);
  if (!targetStep) {
    throw new Error(`${step} 단계에 해당하는 컴포넌트가 존재하지 않습니다.`);
  }
  return <>{targetStep}</>;
};

Funnel.Step = Step;

const useFunnel = <T extends string>({ defaultStep, stepList }: UseFunnelProps<T>) => {
  const [step, setStep] = useState(defaultStep);
  const currentIndex = stepList.indexOf(step);

  if (!stepList.includes(defaultStep)) {
    throw new Error('defaultStep은 반드시 stepList에 포함되어 있어야 합니다.');
  }

  const moveToNextStep = () => {
    const hasNext = currentIndex < stepList.length - 1;
    if (!hasNext) return;
    setStep(stepList[currentIndex + 1]);
  };

  const moveToPrevStep = () => {
    const hasPrev = currentIndex > 0;
    if (!hasPrev) return;
    setStep(stepList[currentIndex - 1]);
  };

  return {
    Funnel,
    Step,
    step,
    moveToNextStep,
    moveToPrevStep,
  };
};

export default useFunnel;
