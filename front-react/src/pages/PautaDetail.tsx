import { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { ArrowLeft, BarChart3, Vote } from 'lucide-react';
import * as Sentry from "@sentry/react";
import { usePauta } from '../hooks/usePauta';
import { useVoting } from '../hooks/useVoting';
import type { VotoOpcao } from '../types';
import { PautaHeader } from '../components/PautaHeader';
import { ResultSummary } from '../components/ResultSummary';
import { VoteForm } from '../components/VoteForm';
import { CountdownTimer } from '../components/CountdownTimer';

type FlowStep = 'INFO' | 'VOTING' | 'RESULT';
const DEFAULT_SESSION_MINUTES = 1; // Tempo padrão da sessão se não vier nada do config

export default function PautaDetail() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [step, setStep] = useState<FlowStep>('INFO');
  const { pauta, resultado, loading, fetchPauta, fetchResultado } = usePauta(id);
  const { acting, handleOpenSession, handleVote } = useVoting(id);

  const onStartVotingFlow = async () => {
    try {
      if (pauta?.sessaoAtiva) {
        setStep('VOTING'); // Se já tá aberta, vai direto pro voto
      } else {
        // Abre a sessão no primeiro clique pra facilitar a vida do associado
        const success = await handleOpenSession(DEFAULT_SESSION_MINUTES);
        if (success) {
          await fetchPauta();
          setStep('VOTING');
        }
      }
    } catch (error) {
      Sentry.captureException(error);
    }
  };
  
  const onVote = async (cpf: string, opcao: VotoOpcao) => {
    try {
      const success = await handleVote(cpf, opcao);
      if (success) { 
        setStep('RESULT'); 
        setTimeout(fetchResultado, 1000); // Dá um tempinho pro backend processar antes de atualizar
      }
    } catch (error) {
      Sentry.captureException(error);
    }
  };

  if (loading) return (
    <div className="py-20 text-center animate-pulse text-slate-400 font-bold uppercase tracking-widest" role="status">
      Colhendo Pauta...
    </div>
  );
  
  if (!pauta) return (
    <div className="p-20 text-center text-red-500 font-bold" role="alert">
      Pauta não encontrada.
    </div>
  );

  return (
    <div className="space-y-8 pb-10" role="main">
      <header className="px-2 pt-10">
        <button 
          onClick={() => navigate(-1)} 
          className="flex items-center gap-2 text-slate-400 font-bold text-[10px] uppercase hover:text-[#008a51] transition-colors"
          aria-label="Voltar para a lista de pautas"
        >
          <ArrowLeft size={14} aria-hidden="true" /> Voltar
        </button>
      </header>
      
      {step === 'INFO' && (
        <section className="space-y-12 animate-in fade-in duration-700">
          <PautaHeader pauta={pauta} />
          <div className="flex flex-col gap-4 p-4">
             <button 
               onClick={onStartVotingFlow} 
               disabled={acting}
               className="btn-primary w-full py-6 text-xl shadow-green-900/20"
               aria-label="Iniciar fluxo de votação"
             >
               {acting ? 'Iniciando Sessão...' : <><Vote size={24} aria-hidden="true" /> Quero Votar</>}
             </button>
             <button 
               onClick={() => { setStep('RESULT'); fetchResultado(); }} 
               className="btn-secondary w-full py-6 text-xl opacity-70"
               aria-label="Ver resultados parciais"
             >
               <BarChart3 size={24} aria-hidden="true" /> Ver Resultados
             </button>
          </div>
        </section>
      )}

      {step === 'VOTING' && (
        <section className="space-y-8 animate-in slide-in-from-bottom-5">
           <div className="flex justify-center">
              <div className="bg-[#008a51] text-white px-6 py-2 rounded-full shadow-lg shadow-green-900/20 flex items-center gap-3">
                 <span className="text-[10px] font-bold uppercase tracking-widest">Tempo Restante:</span>
                 <CountdownTimer 
                   targetDate={pauta.dataFechamentoSessao} 
                   onEnd={() => {
                     setStep('RESULT');
                     fetchResultado();
                   }} 
                 />
              </div>
           </div>
           <VoteForm onVote={onVote} acting={acting} />
        </section>
      )}

      {step === 'RESULT' && (
        <section className="space-y-8 animate-in fade-in">
           <PautaHeader pauta={pauta} />
           <ResultSummary resultado={resultado} onRefresh={fetchResultado} />
           <button 
             onClick={() => setStep('INFO')} 
             className="btn-secondary w-full"
             aria-label="Sair do resultado para detalhes da pauta"
           >
             Sair
           </button>
        </section>
      )}
    </div>
  );
}
