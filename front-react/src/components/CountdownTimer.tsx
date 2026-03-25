import React, { useState, useEffect } from 'react';
import { Clock } from 'lucide-react';

interface Props {
  targetDate: string | undefined;
  onEnd?: () => void;
  className?: string;
}

export const CountdownTimer: React.FC<Props> = ({ targetDate, onEnd, className }) => {
  const [timeLeft, setTimeLeft] = useState<string>('--:--');

  useEffect(() => {
    if (!targetDate) return;

    const calculate = () => {
      const difference = new Date(targetDate).getTime() - new Date().getTime();
      
      if (difference <= 0) {
        setTimeLeft('00:00');
        onEnd?.();
        return false;
      }

      const minutes = Math.floor((difference / 1000 / 60) % 60);
      const seconds = Math.floor((difference / 1000) % 60);
      
      setTimeLeft(
        `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`
      );
      return true;
    };

    calculate();
    const timer = setInterval(() => {
      // Se a contagem chegar em zero, a gente para o intervalo pra não gastar recurso à toa
      if (!calculate()) clearInterval(timer);
    }, 1000);

    return () => clearInterval(timer);
  }, [targetDate]);

  return (
    <div 
      className={`flex items-center gap-2 font-mono font-bold ${className}`}
      role="timer" // Importante pra acessibilidade
      aria-label={`Tempo restante: ${timeLeft}`}
    >
      <Clock size={16} className="animate-pulse" aria-hidden="true" />
      <span aria-live="polite" title="Relógio de contagem regressiva">
        {timeLeft}
      </span>
    </div>
  );
};
