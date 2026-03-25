import React, { useState } from 'react';
import { ShieldCheck, ThumbsUp, ThumbsDown } from 'lucide-react';
import type { VotoOpcao } from '../types';

interface Props { onVote: (cpf: string, opcao: VotoOpcao) => void; acting: boolean; }

export const VoteForm: React.FC<Props> = ({ onVote, acting }) => {
  const [cpf, setCpf] = useState('');
  const handleVote = (opcao: VotoOpcao) => onVote(cpf, opcao);
  return (
    <div className="space-y-10 animate-in fade-in slide-in-from-bottom-6 duration-700">
      <div className="text-center space-y-2">
        <div className="inline-flex p-3 bg-green-50 text-[#008a51] rounded-full"><ShieldCheck size={32} /></div>
        <h2 className="text-2xl font-bold text-slate-800">Sua Opinião</h2>
        <p className="text-slate-400 text-sm">Informe seu CPF para validar seu voto.</p>
      </div>
      <div className="space-y-8">
        <input type="text" maxLength={11} className="input-verdant text-center tracking-widest font-mono" value={cpf} onChange={e => setCpf(e.target.value.replace(/\D/g, ''))} placeholder="00000000000" />
        <div className="grid grid-cols-2 gap-4">
          <button onClick={() => handleVote('SIM')} disabled={acting || !cpf} className="flex flex-col items-center gap-3 p-8 rounded-[2rem] border-2 border-green-100 hover:bg-green-500 hover:text-white transition-all disabled:opacity-30 group shadow-lg"><ThumbsUp size={32} /><span className="font-bold">SIM</span></button>
          <button onClick={() => handleVote('NAO')} disabled={acting || !cpf} className="flex flex-col items-center gap-3 p-8 rounded-[2rem] border-2 border-red-50 text-red-500 hover:bg-red-500 hover:text-white transition-all disabled:opacity-30 group shadow-lg"><ThumbsDown size={32} /><span className="font-bold">NÃO</span></button>
        </div>
      </div>
    </div>
  );
};
