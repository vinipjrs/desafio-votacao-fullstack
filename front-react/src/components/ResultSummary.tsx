import React from 'react';
import { BarChart3, RefreshCcw, Users } from 'lucide-react';
import type { ResultadoVotacao } from '../types';

interface Props { resultado: ResultadoVotacao | null; onRefresh: () => void; }

export const ResultSummary: React.FC<Props> = ({ resultado, onRefresh }) => {
  const simPercent = resultado?.totalVotos ? Math.round((resultado.votosSim / resultado.totalVotos) * 100) : 0;
  const naoPercent = resultado?.totalVotos ? Math.round((resultado.votosNao / resultado.totalVotos) * 100) : 0;
  return (
    <div className="glass-card p-8 bg-gradient-to-br from-white/90 to-green-50/50 animate-in zoom-in-95">
      <div className="flex justify-between items-center mb-10">
        <div><h2 className="text-2xl font-bold text-slate-800 flex items-center gap-3"><BarChart3 size={24} className="text-[#008a51]" /> Apuração <span className="text-[#008a51]">Parcial</span></h2></div>
        <button onClick={onRefresh} className="p-3 bg-white rounded-2xl text-slate-400 hover:text-[#008a51] hover:rotate-180 transition-all duration-500"><RefreshCcw size={18} /></button>
      </div>
      <div className="space-y-8">
        <div>
          <div className="flex justify-between mb-3 text-sm font-bold text-slate-700"><span>Favoráveis</span><span className="bg-green-100 text-[#008a51] px-2 py-0.5 rounded-lg">{simPercent}%</span></div>
          <div className="w-full bg-slate-100/50 rounded-full h-4 overflow-hidden"><div className="bg-gradient-to-r from-[#008a51] to-[#00c875] h-full transition-all duration-1000" style={{ width: `${simPercent}%` }} /></div>
        </div>
        <div>
          <div className="flex justify-between mb-3 text-sm font-bold text-slate-700"><span>Contrários</span><span className="bg-red-50 text-red-500 px-2 py-0.5 rounded-lg">{naoPercent}%</span></div>
          <div className="w-full bg-slate-100/50 rounded-full h-4 overflow-hidden"><div className="bg-red-400 h-full transition-all duration-1000" style={{ width: `${naoPercent}%` }} /></div>
        </div>
        <div className="pt-6 border-t flex items-center justify-between">
          <span className="text-slate-500 font-bold text-[10px] uppercase tracking-widest flex items-center gap-2"><Users size={14} /> Total</span>
          <span className="text-xl font-bold">{resultado?.totalVotos || 0}</span>
        </div>
      </div>
    </div>
  );
};
