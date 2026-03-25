import React from 'react';
import type { Pauta } from '../types';
import { Calendar, Tag } from 'lucide-react';
import { CountdownTimer } from './CountdownTimer';

interface Props { pauta: Pauta; }

export const PautaHeader: React.FC<Props> = ({ pauta }) => (
  <div className="space-y-4 animate-in fade-in slide-in-from-left-4 duration-500">
    <div className="flex items-center gap-3">
      {pauta.sessaoAtiva ? (
        <span className="status-badge bg-[#008a51] text-white flex items-center gap-2">
          <CountdownTimer targetDate={pauta.dataFechamentoSessao} className="text-[10px]" />
          Sessão Aberta
        </span>
      ) : (
        <span className="status-badge bg-green-50 text-[#008a51] border border-green-100/50 flex items-center gap-1">
          <Tag size={10} /> Pauta Ativa
        </span>
      )}
      <span className="text-[10px] font-bold text-slate-400 uppercase tracking-widest flex items-center gap-1"><Calendar size={12} /> {new Date(pauta.criadoEm).toLocaleDateString()}</span>
    </div>
    <h1 className="text-3xl font-bold text-slate-800 tracking-tight leading-tight">{pauta.titulo}</h1>
    <div className="glass-card p-6 bg-white/40 shadow-none border-dashed border-2 border-green-100/50">
      <p className="text-slate-600 leading-relaxed font-medium">{pauta.descricao}</p>
    </div>
  </div>
);
