import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import * as Sentry from "@sentry/react";
import './index.css'
import App from './App.tsx'

// Config do Sentry pra gente não voar às cegas em prod
Sentry.init({
  // TODO: Trocar pela chave real do projeto
  dsn: "https://examplePublicKey@o0.ingest.sentry.io/0", 
  integrations: [
    Sentry.browserTracingIntegration(),
    Sentry.replayIntegration(),
  ],
  tracesSampleRate: 1.0,
  replaysSessionSampleRate: 0.1,
  replaysOnErrorSampleRate: 1.0,
});

const container = document.getElementById('root');
if (container) {
  createRoot(container).render(
    <StrictMode>
      {/* ErrorBoundary pra garantir que o app não morra se um componente falhar */}
      <Sentry.ErrorBoundary fallback={<p className="p-10 text-center font-bold">Ops! Tivemos um probleminha. Tenta dar um F5? 🔄</p>}>
        <App />
      </Sentry.ErrorBoundary>
    </StrictMode>,
  )
}
